package ru.l2gw.gameserver.serverpackets;

import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.gameserver.instancemanager.CastleManorManager;
import ru.l2gw.gameserver.instancemanager.CastleManorManager.CropProcure;
import ru.l2gw.gameserver.instancemanager.ResidenceManager;
import ru.l2gw.gameserver.model.L2Manor;
import ru.l2gw.gameserver.model.entity.Castle;

/**
 * format(packet 0xFE)
 * ch dd [ddcdcdddddddcddc]
 * c  - id
 * h  - sub id
 *
 * d  - manor id
 * d  - size
 *
 * [
 * d  - crop id
 * d  - seed level
 * c
 * d  - reward 1 id
 * c
 * d  - reward 2 id
 * d  - next sale limit
 * d
 * d  - min crop price
 * d  - max crop price
 * d  - today buy
 * d  - today price
 * c  - today reward
 * d  - next buy
 * d  - next price
 * c  - next reward
 * ]
 */
public class ExShowCropSetting extends L2GameServerPacket
{
	private int _manorId;
	private int _count;
	private long[] _cropData; // data to send, size:_count*14

	public ExShowCropSetting(int manorId)
	{
		_manorId = manorId;
		Castle castle = ResidenceManager.getInstance().getCastleById(_manorId);
		GArray<Integer> crops = L2Manor.getInstance().getCropsForCastle(_manorId);
		_count = crops.size();
		_cropData = new long[_count * 14];
		int i = 0;
		for(int cr : crops)
		{
			_cropData[i * 14 + 0] = cr;
			_cropData[i * 14 + 1] = L2Manor.getInstance().getSeedLevelByCrop(cr);
			_cropData[i * 14 + 2] = L2Manor.getInstance().getRewardItem(cr, 1);
			_cropData[i * 14 + 3] = L2Manor.getInstance().getRewardItem(cr, 2);
			_cropData[i * 14 + 4] = L2Manor.getInstance().getCropPuchaseLimit(cr);
			_cropData[i * 14 + 5] = 0; // Looks like not used
			_cropData[i * 14 + 6] = L2Manor.getInstance().getCropBasicPrice(cr) * 60 / 100;
			_cropData[i * 14 + 7] = L2Manor.getInstance().getCropBasicPrice(cr) * 10;
			CropProcure cropPr = castle.getCrop(cr, CastleManorManager.PERIOD_CURRENT);
			if(cropPr != null)
			{
				_cropData[i * 14 + 8] = cropPr.getStartAmount();
				_cropData[i * 14 + 9] = cropPr.getPrice();
				_cropData[i * 14 + 10] = cropPr.getReward();
			}
			else
			{
				_cropData[i * 14 + 8] = 0;
				_cropData[i * 14 + 9] = 0;
				_cropData[i * 14 + 10] = 0;
			}
			cropPr = castle.getCrop(cr, CastleManorManager.PERIOD_NEXT);
			if(cropPr != null)
			{
				_cropData[i * 14 + 11] = cropPr.getStartAmount();
				_cropData[i * 14 + 12] = cropPr.getPrice();
				_cropData[i * 14 + 13] = cropPr.getReward();
			}
			else
			{
				_cropData[i * 14 + 11] = 0;
				_cropData[i * 14 + 12] = 0;
				_cropData[i * 14 + 13] = 0;
			}
			i++;
		}
	}

	@Override
	public void writeImpl()
	{
		writeC(EXTENDED_PACKET); // Id
		writeH(0x2b); // SubId

		writeD(_manorId); // manor id
		writeD(_count); // size

		for(int i = 0; i < _count; i++)
		{
			writeD((int) _cropData[i * 14 + 0]); // crop id
			writeD((int) _cropData[i * 14 + 1]); // seed level
			writeC(1);
			writeD((int) _cropData[i * 14 + 2]); // reward 1 id
			writeC(1);
			writeD((int) _cropData[i * 14 + 3]); // reward 2 id

			writeD((int) _cropData[i * 14 + 4]); // next sale limit
			writeD((int) _cropData[i * 14 + 5]); // ???
			writeD((int) _cropData[i * 14 + 6]); // min crop price
			writeD((int) _cropData[i * 14 + 7]); // max crop price

			writeQ(_cropData[i * 14 + 8]); // today buy
			writeQ(_cropData[i * 14 + 9]); // today price
			writeC((int) _cropData[i * 14 + 10]); // today reward

			writeQ(_cropData[i * 14 + 11]); // next buy
			writeQ(_cropData[i * 14 + 12]); // next price
			writeC((int) _cropData[i * 14 + 13]); // next reward
		}
	}
}