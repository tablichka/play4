package ru.l2gw.commons.network;

import java.nio.ByteBuffer;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayDeque;
import java.util.Queue;

public class MMOConnection<T extends MMOClient<?>>
{
	private final SelectorThread<T> _selectorThread;
	private T _client;

	private ISocket _socket;
	private WritableByteChannel _writableByteChannel;
	private ReadableByteChannel _readableByteChannel;

	private Queue<SendablePacket<T>> _sendQueue = new ArrayDeque<SendablePacket<T>>();
	private SelectionKey _selectionKey;

	private int _readHeaderPending;
	private ByteBuffer _readBuffer;

	private ByteBuffer _primaryWriteBuffer;
	private ByteBuffer _secondaryWriteBuffer;

	private boolean _pendingClose;

	public MMOConnection(SelectorThread<T> selectorThread, ISocket socket, SelectionKey key)
	{
		_selectorThread = selectorThread;
		this.setSocket(socket);
		this.setWritableByteChannel(socket.getWritableByteChannel());
		this.setReadableByteChannel(socket.getReadableByteChannel());
		this.setSelectionKey(key);
	}

	public MMOConnection(T client, SelectorThread<T> selectorThread, ISocket socket, SelectionKey key)
	{
		this(selectorThread, socket, key);
		this.setClient(client);
	}

	public MMOConnection(SelectorThread<T> selectorThread)
	{
		_selectorThread = selectorThread;
	}

	protected void setClient(T client)
	{
		_client = client;
	}

	public T getClient()
	{
		return _client;
	}

	public void sendPacket(SendablePacket<T> sp)
	{
		sp.setClient(_client);
		sp.runImpl();
		synchronized (_sendQueue)
		{
			if(!_pendingClose)
				try
				{
					getSelectionKey().interestOps(getSelectionKey().interestOps() | SelectionKey.OP_WRITE);
					getSendQueue().add(sp);
				}
				catch(CancelledKeyException e)
				{
					// ignore
				}
		}
	}

	protected SelectorThread<T> getSelectorThread()
	{
		return _selectorThread;
	}

	protected void setSelectionKey(SelectionKey key)
	{
		_selectionKey = key;
	}

	protected SelectionKey getSelectionKey()
	{
		return _selectionKey;
	}

	protected void enableReadInterest()
	{
		try
		{
			getSelectionKey().interestOps(getSelectionKey().interestOps() | SelectionKey.OP_READ);
		}
		catch(CancelledKeyException e)
		{
			// ignore
		}
	}

	protected void disableReadInterest()
	{
		try
		{
			getSelectionKey().interestOps(getSelectionKey().interestOps() & ~SelectionKey.OP_READ);
		}
		catch(CancelledKeyException e)
		{
			// ignore
		}
	}

	protected void enableWriteInterest()
	{
		try
		{
			getSelectionKey().interestOps(getSelectionKey().interestOps() | SelectionKey.OP_WRITE);
		}
		catch(CancelledKeyException e)
		{
			// ignore
		}
	}

	protected void disableWriteInterest()
	{
		try
		{
			getSelectionKey().interestOps(getSelectionKey().interestOps() & ~SelectionKey.OP_WRITE);
		}
		catch(CancelledKeyException e)
		{
			// ignore
		}
	}

	/**
	 * @param socket the socket to set
	 */
	protected void setSocket(ISocket socket)
	{
		_socket = socket;
	}

	/**
	 * @return the socket
	 */
	public ISocket getSocket()
	{
		return _socket;
	}

	protected void setWritableByteChannel(WritableByteChannel wbc)
	{
		_writableByteChannel = wbc;
	}

	public WritableByteChannel getWritableChannel()
	{
		return _writableByteChannel;
	}

	protected void setReadableByteChannel(ReadableByteChannel rbc)
	{
		_readableByteChannel = rbc;
	}

	public ReadableByteChannel getReadableByteChannel()
	{
		return _readableByteChannel;
	}

	protected Queue<SendablePacket<T>> getSendQueue()
	{
		return _sendQueue;
	}

	protected void createWriteBuffer(ByteBuffer buf)
	{
		if(_primaryWriteBuffer == null)
		{
			//System.err.println("APPENDING FOR NULL");
			//System.err.flush();
			_primaryWriteBuffer = this.getSelectorThread().getPooledBuffer();
			_primaryWriteBuffer.put(buf);
		}
		else
		{
			//System.err.println("PREPENDING ON EXISTING");
			//System.err.flush();

			ByteBuffer temp = this.getSelectorThread().getPooledBuffer();
			temp.put(buf);

			int remaining = temp.remaining();
			_primaryWriteBuffer.flip();
			int limit = _primaryWriteBuffer.limit();

			if(remaining >= _primaryWriteBuffer.remaining())
			{
				temp.put(_primaryWriteBuffer);
				this.getSelectorThread().recycleBuffer(_primaryWriteBuffer);
				_primaryWriteBuffer = temp;
			}
			else
			{
				_primaryWriteBuffer.limit(remaining);
				temp.put(_primaryWriteBuffer);
				_primaryWriteBuffer.limit(limit);
				_primaryWriteBuffer.compact();
				_secondaryWriteBuffer = _primaryWriteBuffer;
				_primaryWriteBuffer = temp;
			}
		}
	}

	/*
	protected void appendIntoWriteBuffer(ByteBuffer buf)
	{
	    // if we already have a buffer
	    if (_secondaryWriteBuffer != null && (_primaryWriteBuffer != null && !_primaryWriteBuffer.hasRemaining()))
	    {
	        _secondaryWriteBuffer.put(buf);

	        if (MMOCore.ASSERTIONS_ENABLED)
	        {
	            // correct state
	            assert _primaryWriteBuffer == null || !_primaryWriteBuffer.hasRemaining();
	            // full write
	            assert !buf.hasRemaining();
	        }
	    }
	    else if (_primaryWriteBuffer != null)
	    {
	        int size = Math.min(buf.limit(), _primaryWriteBuffer.remaining());
	        _primaryWriteBuffer.put(buf.array(), buf.position(), size);
	        buf.position(buf.position() + size);

	        // primary wasnt enough
	        if (buf.hasRemaining())
	        {
	            _secondaryWriteBuffer = this.getSelectorThread().getPooledBuffer();
	            _secondaryWriteBuffer.put(buf);
	        }

	        if (MMOCore.ASSERTIONS_ENABLED)
	        {
	            // full write
	            assert !buf.hasRemaining();
	        }
	    }
	    else
	    {
	        // a single empty buffer should be always enough by design
	        _primaryWriteBuffer = this.getSelectorThread().getPooledBuffer();
	        _primaryWriteBuffer.put(buf);
	        System.err.println("ESCREVI "+_primaryWriteBuffer.position());
	        if (MMOCore.ASSERTIONS_ENABLED)
	        {
	            // full write
	            assert !buf.hasRemaining();
	        }
	    }
	}*/

	/*protected void prependIntoPendingWriteBuffer(ByteBuffer buf)
	{
	    int remaining = buf.remaining();

	    //do we already have some buffer
	    if (_primaryWriteBuffer != null && _primaryWriteBuffer.hasRemaining())
	    {
	        if (remaining == _primaryWriteBuffer.capacity())
	        {
	            if (MMOCore.ASSERTIONS_ENABLED)
	            {
	                assert _secondaryWriteBuffer == null;
	            }

	            _secondaryWriteBuffer = _primaryWriteBuffer;
	            _primaryWriteBuffer = this.getSelectorThread().getPooledBuffer();
	            _primaryWriteBuffer.put(buf);
	        }
	        else if (remaining < _primaryWriteBuffer.remaining())
	        {

	        }
	    }
	    else
	    {

	    }
	}*/

	protected boolean hasPendingWriteBuffer()
	{
		return _primaryWriteBuffer != null;
	}

	protected void movePendingWriteBufferTo(ByteBuffer dest)
	{
		//System.err.println("PRI SIZE: "+_primaryWriteBuffer.position());
		//System.err.flush();
		_primaryWriteBuffer.flip();
		dest.put(_primaryWriteBuffer);
		this.getSelectorThread().recycleBuffer(_primaryWriteBuffer);
		_primaryWriteBuffer = _secondaryWriteBuffer;
		_secondaryWriteBuffer = null;
	}

	/*protected void finishPrepending(int written)
	{
	    _primaryWriteBuffer.position(Math.min(written, _primaryWriteBuffer.limit()));
	    // discard only the written bytes
	    _primaryWriteBuffer.compact();

	    if (_secondaryWriteBuffer != null)
	    {
	        _secondaryWriteBuffer.flip();
	        _primaryWriteBuffer.put(_secondaryWriteBuffer);

	        if (!_secondaryWriteBuffer.hasRemaining())
	        {
	            this.getSelectorThread().recycleBuffer(_secondaryWriteBuffer);
	            _secondaryWriteBuffer = null;
	        }
	        else
	        {
	            _secondaryWriteBuffer.compact();
	        }
	    }
	}*/

	protected ByteBuffer getWriteBuffer()
	{
		ByteBuffer ret = _primaryWriteBuffer;
		if(_secondaryWriteBuffer != null)
		{
			_primaryWriteBuffer = _secondaryWriteBuffer;
			_secondaryWriteBuffer = null;
		}
		return ret;
	}

	protected void setPendingHeader(int size)
	{
		_readHeaderPending = size;
	}

	protected int getPendingHeader()
	{
		return _readHeaderPending;
	}

	protected void setReadBuffer(ByteBuffer buf)
	{
		_readBuffer = buf;
	}

	protected ByteBuffer getReadBuffer()
	{
		return _readBuffer;
	}

	public boolean isClosed()
	{
		return _pendingClose;
	}

	protected void closeNow(boolean error)
	{
		synchronized (_sendQueue)
		{
			if(!isClosed())
			{
				_pendingClose = true;
				getSendQueue().clear();
				if(!error)
					disableWriteInterest();
				getSelectorThread().closeConnection(this);
			}
		}
	}

	public void close(SendablePacket<T> sp)
	{
		synchronized (_sendQueue)
		{
			if(isClosed())
				return;
			getSendQueue().clear();
		}
		sendPacket(sp);
		synchronized (_sendQueue)
		{
			_pendingClose = true;
			getSelectorThread().closeConnection(this);
		}
	}

	protected void closeLater()
	{
		synchronized (_sendQueue)
		{
			if(!isClosed())
			{
				_pendingClose = true;
				getSelectorThread().closeConnection(this);
			}
		}
	}

	protected void releaseBuffers()
	{
		if(_primaryWriteBuffer != null)
		{
			this.getSelectorThread().recycleBuffer(_primaryWriteBuffer);
			_primaryWriteBuffer = null;
			if(_secondaryWriteBuffer != null)
			{
				this.getSelectorThread().recycleBuffer(_secondaryWriteBuffer);
				_secondaryWriteBuffer = null;
			}
		}
		if(_readBuffer != null)
		{
			this.getSelectorThread().recycleBuffer(_readBuffer);
			_readBuffer = null;
		}
	}

	protected void onDisconnection()
	{
		this.getClient().onDisconnection();
	}

	protected void onForcedDisconnection()
	{
		this.getClient().onForcedDisconnection();
	}
}
