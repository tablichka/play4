onerror=handleErr
var txt=""

var d=new _pdocument();

function _pdocument()
{
	var _alldata=new Array();
	function _getElementById(id) {
		if (_alldata[id]) {			
			return _alldata[id];
		}
		else {
			var s=document.getElementById(id);
			_alldata[id]=s;
			return _alldata[id];
		}
	}
	this.gI=_getElementById;

}

function handleErr(msg,url,l)
{
txt="There was an error on this page.\n\n"
txt+="Error: " + msg + "\n"
txt+="URL: " + url + "\n"
txt+="Line: " + l + "\n\n"
txt+="Click OK to continue.\n\n"
alert(txt)
return true
}

function calc(classreset,sareset)
{
if (sareset) {
	d.gI("WPNSA").selectedIndex=0
}
//placeholders
var AddASPD=0
var AddCAST=0
var AddCOMBATCRIT=0
var AddCP=0
var AddCPR=0
var AddCRIT=0
var AddCRIT60=0
var AddCRIT30=0
var AddCRITDMG=0
var AddHPR=0
var AddMPR=0
var AddMPR2=0
var AddSHIELDPDEF=0
var AddSPEED=0
var AddSPEED60=0
var AddSPEED30=0
var AddHP=0
var AddMP=0
var AddMATK=0
var AddPATK=0
var AddPATK60=0
var AddPATK30=0
var AddMDEF=0
var AddMDEF60=0
var AddMDEF30=0
var AddPDEF=0
var AddPDEF60=0
var AddPDEF30=0
var AddSPEED=0
var AddWEIGHT=0
var ALType=0
var AType=0
var AUType=0
var BaseCON=0
var BaseSTR=0
var BaseDEX=0
var BaseINT=0
var BaseMEN=0
var BaseWIT=0
var BaseCP=0
var BaseHP=0
var BaseMP=0
var BaseRun=0
var block360=false
var Boot=0
var bowdelay=1500
var BuffACC=0
var BuffACC60=0
var BuffACC30=0
var BuffASPD=1
var BuffASPD60=1
var BuffASPD30=1
var BuffCAST=1
var BuffCOMBATCRIT=1
var BuffCP=1
var BuffCPR=1
var BuffCRITDMG=1
var BuffDAMAGE=1
var BuffEVA=0
var BuffEVA60=0
var BuffEVA30=0
var BuffHEAL=1
var BuffHP=1
var BuffHPR=1
var BuffHPR2=1
var BuffMATK=1
var BuffMDEF=1
var BuffMDEF60=1
var BuffMDEF30=1
var BuffMP=1
var BuffMPR=1
var BuffMPR2=1
var BuffPATK=1
var BuffPATK60=1
var BuffPATK30=1
var BuffPDEF=1
var BuffPDEF60=1
var BuffPDEF30=1
var BuffRD=0
var BuffShieldBlockRate=1
var BuffSHIELDPDEF=1
var BuffSPEED=1
var BuffSPEED60=1
var BuffSPEED30=1
var BuffVR=0
var ClassType=0
var Cloak=0
var combatevasion=0
var CON=0
var CONMOD=0
var DEX=0
var DEXMOD=0
var Glove=0
var GPA="<font color=red><b>Grade Penalty applied</b></font>"
var Earring1=0
var Earring2=0
var EARRINGOFANTHARASVR=0
var EARRINGOFZAKENVR=0
var EBuffDAMAGE=1
var FRINTEZZARD=0
var fullbody=0
var Helmet=0
var INT=0
var INTMOD=0
var Lower=0
var MasteryMATK=0
var MasteryPATK=1
var MEN=0
var MENMOD=0
var Necklace=0
var NECKLACEHP=0
var NECKLACEMATK=1
var NECKLACEPATK=1
var overMATK=0
var overPATK=0
var pen=0
var position=d.gI("POS").value
var Ring1=0
var Ring2=0
var RINGOFBAIUMACC=0
var RINGOFBAIUMCRITDMG=1
var RINGOFCOREACC=0
var RINGOFQUEENACC=0
var RINGOFQUEENCRITDMG=1
var RINGOFBAIUMASPD=1
var RINGOFBAIUMCAST=1
var Shield=0
var BaseShieldBlockRate=0.2
var EBaseShieldBlockRate=0.2
var ShieldEvasion=0
var STR=0
var STRMOD=0
var subcritical=0
var underMATK=0
var underMDEF=0
var underPATK=0
var helmetoverPDEF=0
var upperoverPDEF=0
var loweroverPDEF=0
var bootoverPDEF=0
var gloveoverPDEF=0
var necklaceoverPDEF=0
var earring1overPDEF=0
var earring2overPDEF=0
var ring1overPDEF=0
var ring2overPDEF=0
var helmetunderPDEF=0
var upperunderPDEF=0
var lowerunderPDEF=0
var bootunderPDEF=0
var gloveunderPDEF=0
var necklaceunderPDEF=0
var earring1underPDEF=0
var earring2underPDEF=0
var ring1underPDEF=0
var ring2underPDEF=0
var shield=0
var Upper=0
var VALAKASRD=0
var weightlimit=69000
var weightpenalty=0
var WIT=0
var WITMOD=0
var WpnAcc=0
var WpnMATK=0
var WpnType="unequipped"

//Level Modifier
var LVL = + d.gI("LV").value
var LVLMOD=(LVL+89)/100

var JOB=d.gI("job").value
JOB=PROFESSIONS[JOB]
if (JOB=="DA"||JOB=="GL"||JOB=="HE"||JOB=="HF"||JOB=="HK"||JOB=="PA"||JOB=="RO"||JOB=="TH"||JOB=="WD"||JOB=="WA")
	{race="HF"}
else if (JOB=="BI"||JOB=="CL"||JOB=="HM"||JOB=="NE"||JOB=="PP"||JOB=="SOR"||JOB=="WL"||JOB=="WI")
	{race="HM"}
else if (JOB=="EF"||JOB=="EK"||JOB=="EFS"||JOB=="PW"||JOB=="SW"||JOB=="TK"||JOB=="SR")
	{race="EF"}
else if (JOB=="ELS"||JOB=="EE"||JOB=="EM"||JOB=="EO"||JOB=="EW"||JOB=="SPS")
	{race="EM"}
else if (JOB=="AW"||JOB=="AS"||JOB=="BD"||JOB=="DF"||JOB=="PK"||JOB=="PR"||JOB=="SK")
	{race="DF"}
else if (JOB=="DM"||JOB=="DW"|JOB=="PS"||JOB=="SHE"|JOB=="SO"||JOB=="SPH")
	{race="DM"}
else if (JOB=="DE"||JOB=="MO"||JOB=="OF"||JOB=="OR"|JOB=="TY")
	{race="OF"}
else if (JOB=="OM"||JOB=="OS"||JOB=="OL"||JOB=="WC")
	{race="OM"}
else if (JOB=="AR"||JOB=="BH"||JOB=="DO"||JOB=="SC"||JOB=="WS")
	{race="DW"}

if (race=="HF")
{BaseSTR=40;BaseCON=43;BaseDEX=30;BaseINT=21;BaseWIT=11;BaseMEN=25;BaseRun=115;BaseWalk=80;}
else if (race=="HM")
{BaseSTR=22;BaseCON=27;BaseDEX=21;BaseINT=41;BaseWIT=20;BaseMEN=39;BaseRun=120;BaseWalk=78;}
else if (race=="EF")
{BaseSTR=36;BaseCON=36;BaseDEX=35;BaseINT=23;BaseWIT=14;BaseMEN=26;BaseRun=125;BaseWalk=90;}
else if (race=="EM")
{BaseSTR=21;BaseCON=25;BaseDEX=24;BaseINT=37;BaseWIT=23;BaseMEN=40;BaseRun=122;BaseWalk=85;}
else if (race=="DF")
{BaseSTR=41;BaseCON=32;BaseDEX=34;BaseINT=25;BaseWIT=12;BaseMEN=26;BaseRun=122;BaseWalk=85;}
else if (race=="DM")
{BaseSTR=23;BaseCON=24;BaseDEX=23;BaseINT=44;BaseWIT=19;BaseMEN=37;BaseRun=122;BaseWalk=85;}
else if (race=="OF")
{BaseSTR=40;BaseCON=47;BaseDEX=26;BaseINT=18;BaseWIT=12;BaseMEN=27;BaseRun=117;BaseWalk=70;}
else if (race=="OM")
{BaseSTR=27;BaseCON=31;BaseDEX=24;BaseINT=31;BaseWIT=15;BaseMEN=42;BaseRun=121;BaseWalk=70;}
else if (race=="DW")
{BaseSTR=39;BaseCON=45;BaseDEX=29;BaseINT=20;BaseWIT=10;BaseMEN=27;BaseRun=115;BaseWalk=80;}

//default selections when class is changed
if (classreset){
	d.gI("ts1f").selectedIndex=0
	d.gI("ts2f").selectedIndex=0
	d.gI("ts3f").selectedIndex=0
	d.gI("ts1n").selectedIndex=0
	d.gI("ts2n").selectedIndex=0
	d.gI("ts3n").selectedIndex=0
	d.gI("ts1h").selectedIndex=0
	d.gI("ts2h").selectedIndex=0
	d.gI("ts3h").selectedIndex=0
	}

//Tattoo Restrictions by class
if (JOB=="AW"||JOB=="AR"||JOB=="AS"||JOB=="BD"||JOB=="BH"||JOB=="DA"||JOB=="DE"||JOB=="EK"||JOB=="EFS"||JOB=="GL"||JOB=="HE"||JOB=="HK"||JOB=="MO"||JOB=="OR"||JOB=="PA"||JOB=="PK"||JOB=="PR"||JOB=="PW"||JOB=="RO"||JOB=="SC"||JOB=="SK"||JOB=="SR"||JOB=="SW"||JOB=="TK"||JOB=="TH"||JOB=="TY"||JOB=="WD"||JOB=="WA"||JOB=="WS"||JOB=="EF"||JOB=="DF"||JOB=="OR"||JOB=="DO"||JOB=="HF"||JOB=="OF")
{
d.gI("ts1f").style.display = 'block'
d.gI("ts2f").style.display = 'block'
d.gI("ts3f").style.display = 'block'
d.gI("ts1h").style.display = 'none'
d.gI("ts2h").style.display = 'none'
d.gI("ts3h").style.display = 'none'
d.gI("ts1n").style.display = 'none'
d.gI("ts2n").style.display = 'none'
d.gI("ts3n").style.display = 'none'
if (d.gI("TTT1C").checked==true)
	{var TAT1=d.gI("ts1f").value}
else {var TAT1=0}
if (d.gI("TTT2C").checked==true)
	{var TAT2=d.gI("ts2f").value}
else {var TAT2=0}
if (d.gI("TTT3C").checked==true)
	{var TAT3=d.gI("ts3f").value}
else {var TAT3=0}
}
else if (JOB=="EO"||JOB=="CL"||JOB=="PP"||JOB=="BI"||JOB=="EE"||JOB=="SO"||JOB=="SHE"||JOB=="OS"||JOB=="WC"||JOB=="OL"||JOB=="OM")
{
d.gI("ts1f").style.display = 'none'
d.gI("ts2f").style.display = 'none'
d.gI("ts3f").style.display = 'none'
d.gI("ts1h").style.display = 'block'
d.gI("ts2h").style.display = 'block'
d.gI("ts3h").style.display = 'block'
d.gI("ts1n").style.display = 'none'
d.gI("ts2n").style.display = 'none'
d.gI("ts3n").style.display = 'none'
if (d.gI("TTT1C").checked==true)
	{var TAT1=d.gI("ts1h").value}
else {var TAT1=0}
if (d.gI("TTT2C").checked==true)
	{var TAT2=d.gI("ts2h").value}
else {var TAT2=0}
if (d.gI("TTT3C").checked==true)
	{var TAT3=d.gI("ts3h").value}
else {var TAT3=0}
}
else if (JOB=="WI"||JOB=="SOR"||JOB=="NE"||JOB=="WL"||JOB=="EW"||JOB=="SPS"||JOB=="ELS"||JOB=="DW"||JOB=="PS"||JOB=="SPH"||JOB=="DM"||JOB=="EM"||JOB=="HM")
{
d.gI("ts1f").style.display = 'none'
d.gI("ts2f").style.display = 'none'
d.gI("ts3f").style.display = 'none'
d.gI("ts1h").style.display = 'none'
d.gI("ts2h").style.display = 'none'
d.gI("ts3h").style.display = 'none'
d.gI("ts1n").style.display = 'block'
d.gI("ts2n").style.display = 'block'
d.gI("ts3n").style.display = 'block'
if (d.gI("TTT1C").checked==true)
	{var TAT1=d.gI("ts1n").value}
else {var TAT1=0}
if (d.gI("TTT2C").checked==true)
	{var TAT2=d.gI("ts2n").value}
else {var TAT2=0}
if (d.gI("TTT3C").checked==true)
	{var TAT3=d.gI("ts3n").value}
else {var TAT3=0}
}

if (JOB=="EF"||JOB=="DF"||JOB=="OF"||JOB=="DO"||JOB=="HF"||JOB=="HM"||JOB=="EM"||JOB=="DM"||JOB=="OM"){
d.gI("TTT2C").disabled=true
d.gI("TTT3C").disabled=true
d.gI("ts2f").disabled=true
d.gI("ts3f").disabled=true
d.gI("ts2h").disabled=true
d.gI("ts3h").disabled=true
d.gI("ts2n").disabled=true
d.gI("ts3n").disabled=true
var TAT2=0
var TAT3=0
}
else if (JOB=="RO"||JOB=="WA"||JOB=="HK"||JOB=="WI"||JOB=="CL"||JOB=="EK"||JOB=="EFS"||JOB=="EO"||JOB=="EW"||JOB=="PK"||JOB=="AS"||JOB=="DW"||JOB=="SO"||JOB=="MO"||JOB=="OR"||JOB=="OS"||JOB=="SC"||JOB=="AR"){
d.gI("TTT2C").disabled=false
d.gI("TTT3C").disabled=true
d.gI("ts2f").disabled=false
d.gI("ts3f").disabled=true
d.gI("ts2h").disabled=false
d.gI("ts3h").disabled=true
d.gI("ts2n").disabled=false
d.gI("ts3n").disabled=true
var TAT3=0
}
else{
d.gI("TTT2C").disabled=false
d.gI("TTT3C").disabled=false
d.gI("ts2f").disabled=false
d.gI("ts3f").disabled=false
d.gI("ts2h").disabled=false
d.gI("ts3h").disabled=false
d.gI("ts2n").disabled=false
d.gI("ts3n").disabled=false
}

///////N
//Tattoos
var TATSTR=STRTAT[TAT1]+STRTAT[TAT2]+STRTAT[TAT3]
if (TATSTR>5)
{TATSTR=5}
var TATDEX=DEXTAT[TAT1]+DEXTAT[TAT2]+DEXTAT[TAT3]
if (TATDEX>5)
{TATDEX=5}
var TATCON=CONTAT[TAT1]+CONTAT[TAT2]+CONTAT[TAT3]
if (TATCON>5)
{TATCON=5}
var TATINT=INTTAT[TAT1]+INTTAT[TAT2]+INTTAT[TAT3]
if (TATINT>5)
{TATINT=5}
var TATWIT=WITTAT[TAT1]+WITTAT[TAT2]+WITTAT[TAT3]
if (TATWIT>5)
{TATWIT=5}
var TATMEN=MENTAT[TAT1]+MENTAT[TAT2]+MENTAT[TAT3]
if (TATMEN>5)
{TATMEN=5}

var STR=TATSTR+BaseSTR
var DEX=TATDEX+BaseDEX
var CON=TATCON+BaseCON
var INT=TATINT+BaseINT
var WIT=TATWIT+BaseWIT
var MEN=TATMEN+BaseMEN

//Base HP/CP Calculation
if (JOB=="HF") {
	BaseHP=68.3+11.635*LVL+0.065*(LVL*LVL)
	CPMOD=0.4
	ClassType="F1"}
else if (JOB=="WA" && LVL<=20) {
	BaseHP=68.3+11.635*LVL+0.065*(LVL*LVL)
	CPMOD=0.8
	ClassType="F1"}
else if (JOB=="WA" && LVL>20) {
	BaseHP=-270+26.85*LVL+0.15*(LVL*LVL)
	CPMOD=0.8
	ClassType="F2"}
else if (JOB=="GL" && LVL<=20) {
	BaseHP=68.3+11.635*LVL+0.065*(LVL*LVL)
	CPMOD=0.9
	ClassType="F1"}
else if (JOB=="GL" && LVL>20 && LVL<=40) {
	BaseHP=-270+26.85*LVL+0.15*(LVL*LVL)
	CPMOD=0.9
	ClassType="F2"}
else if (JOB=="GL" && LVL>40) {
	BaseHP=-620.4+34.01*LVL+0.19*(LVL*LVL)
	CPMOD=0.9
	ClassType="F3"}
else if (JOB=="WD" && LVL<=20) {
	BaseHP=68.3+11.635*LVL+0.065*(LVL*LVL)
	CPMOD=0.8
	ClassType="F1"}
else if (JOB=="WD" && LVL>20 && LVL<=40) {
	BaseHP=-270+26.85*LVL+0.15*(LVL*LVL)
	CPMOD=0.8
	ClassType="F2"}
else if (JOB=="WD" && LVL>40) {
	BaseHP=-795.6+37.59*LVL+0.21*(LVL*LVL)
	CPMOD=0.8
	ClassType="F3"}
else if (JOB=="HK" && LVL<=20) {
	BaseHP=68.3+11.635*LVL+0.065*(LVL*LVL)
	CPMOD=0.6
	ClassType="F1"}
else if (JOB=="HK" && LVL>20) {
	BaseHP=-210.3+24.165*LVL+0.135*(LVL*LVL)
	CPMOD=0.6
	ClassType="F2"}
else if ((JOB=="DA"||JOB=="PA") && LVL<=20) {
	BaseHP=68.3+11.635*LVL+0.065*(LVL*LVL)
	CPMOD=0.6
	ClassType="F1"}
else if ((JOB=="DA"||JOB=="PA") && LVL>20 && LVL<=40) {
	BaseHP=-210.3+24.165*LVL+0.135*(LVL*LVL)
	CPMOD=0.6
	ClassType="F2"}
else if ((JOB=="DA"||JOB=="PA") && LVL>40) {
	BaseHP=-604.5+32.22*LVL+0.18*(LVL*LVL)
	CPMOD=0.6
	ClassType="F3"}
else if (JOB=="RO" && LVL<=20) {
	BaseHP=68.3+11.635*LVL+0.065*(LVL*LVL)
	CPMOD=0.4
	ClassType="F1"}
else if (JOB=="RO" && LVL>20) {
	BaseHP=-170.5+22.375*LVL+0.125*(LVL*LVL)
	CPMOD=0.4
	ClassType="F2"}
else if (JOB=="TH" && LVL<=20) {
	BaseHP=68.3+11.635*LVL+0.065*(LVL*LVL)
	CPMOD=0.4
	ClassType="F1"}
else if (JOB=="TH" && LVL>20 && LVL<=40) {
	BaseHP=-170.5+22.375*LVL+0.125*(LVL*LVL)
	CPMOD=0.4
	ClassType="F2"}
else if (JOB=="TH" && LVL>40) {
	BaseHP=-477.1+28.64*LVL+0.16*(LVL*LVL)
	CPMOD=0.4
	ClassType="F3"}
else if (JOB=="HE" && LVL<=20) {
	BaseHP=68.3+11.635*LVL+0.065*(LVL*LVL)
	CPMOD=0.7
	ClassType="F1"}
else if (JOB=="HE" && LVL>20 && LVL<=40) {
	BaseHP=-170.5+22.375*LVL+0.125*(LVL*LVL)
	CPMOD=0.7
	ClassType="F2"}
else if (JOB=="HE" && LVL>40) {
	BaseHP=-564.7+30.43*LVL+0.17*(LVL*LVL)
	CPMOD=0.7
	ClassType="F3"}
else if (JOB=="HM") {
	BaseHP=85.7+15.215*LVL+0.085*(LVL*LVL)
	CPMOD=0.5
	ClassType="M1"}
else if (JOB=="WI" && LVL<=20) {
	BaseHP=85.7+15.215*LVL+0.085*(LVL*LVL)
	CPMOD=0.5
	ClassType="M1"}
else if (JOB=="WI" && LVL>20) {
	BaseHP=-73.5+22.375*LVL+0.125*(LVL*LVL)
	CPMOD=0.5
	ClassType="M2"}
else if ((JOB=="SOR"||JOB=="NE") && LVL<=20) {
	BaseHP=85.7+15.215*LVL+0.085*(LVL*LVL)
	CPMOD=0.5
	ClassType="M1"}
else if ((JOB=="SOR"||JOB=="NE") && LVL>20 && LVL<=40) {
	BaseHP=-73.5+22.375*LVL+0.125*(LVL*LVL)
	CPMOD=0.5
	ClassType="M2"}
else if ((JOB=="SOR"||JOB=="NE") && LVL>40) {
	BaseHP=-511.5+31.325*LVL+0.175*(LVL*LVL)
	CPMOD=0.5
	ClassType="M3"}
else if (JOB=="WL" && LVL<=20) {
	BaseHP=85.7+15.215*LVL+0.085*(LVL*LVL)
	CPMOD=0.6
	ClassType="M1"}
else if (JOB=="WL" && LVL>20 && LVL<=40) {
	BaseHP=-73.5+22.375*LVL+0.125*(LVL*LVL)
	CPMOD=0.6
	ClassType="M2"}
else if (JOB=="WL" && LVL>40) {
	BaseHP=-642.9+34.01*LVL+0.19*(LVL*LVL)
	CPMOD=0.6
	ClassType="M3"}
else if (JOB=="CL" && LVL<=20) {
	BaseHP=85.7+15.215*LVL+0.085*(LVL*LVL)
	CPMOD=0.5
	ClassType="M1"}
else if (JOB=="CL" && LVL>20) {
	BaseHP=-192.9+27.745*LVL+0.155*(LVL*LVL)
	CPMOD=0.5
	ClassType="M2"}
else if (JOB=="BI" && LVL<=20) {
	BaseHP=85.7+15.215*LVL+0.085*(LVL*LVL)
	CPMOD=0.7
	ClassType="M1"}
else if (JOB=="BI" && LVL>20 && LVL<=40) {
	BaseHP=-192.9+27.745*LVL+0.155*(LVL*LVL)
	CPMOD=0.7
	ClassType="M2"}
else if (JOB=="BI" && LVL>40) {
	BaseHP=-499.5+34.01*LVL+0.19*(LVL*LVL)
	CPMOD=0.7
	ClassType="M3"}
else if (JOB=="PP" && LVL<=20) {
	BaseHP=85.7+15.215*LVL+0.085*(LVL*LVL)
	CPMOD=0.5
	ClassType="M1"}
else if (JOB=="PP" && LVL>20 && LVL<=40) {
	BaseHP=-192.9+27.745*LVL+0.155*(LVL*LVL)
	CPMOD=0.5
	ClassType="M2"}
else if (JOB=="PP" && LVL>40) {
	BaseHP=-630.9+36.695*LVL+0.205*(LVL*LVL)
	CPMOD=0.5
	ClassType="M3"}
else if (JOB=="EF") {
	BaseHP=76.4+12.53*LVL+0.07*(LVL*LVL)
	CPMOD=0.4
	ClassType="F1"}
else if (JOB=="EK" && LVL<=20) {
	BaseHP=76.4+12.53*LVL+0.07*(LVL*LVL)
	CPMOD=0.5
	ClassType="F1"}
else if (JOB=="EK" && LVL>20) {
	BaseHP=-242+26.85*LVL+0.15*(LVL*LVL)
	CPMOD=0.5
	ClassType="F2"}
else if (JOB=="TK" && LVL<=20) {
	BaseHP=76.4+12.53*LVL+0.07*(LVL*LVL)
	CPMOD=0.6
	ClassType="F1"}
else if (JOB=="TK" && LVL>20 && LVL<=40) {
	BaseHP=-242+26.85*LVL+0.15*(LVL*LVL)
	CPMOD=0.6
	ClassType="F2"}
else if (JOB=="TK" && LVL>40) {
	BaseHP=-680+35.8*LVL+0.2*(LVL*LVL)
	CPMOD=0.6
	ClassType="F3"}
else if (JOB=="SW" && LVL<=20) {
	BaseHP=76.4+12.53*LVL+0.07*(LVL*LVL)
	CPMOD=0.5
	ClassType="F1"}
else if (JOB=="SW" && LVL>20 && LVL<=40) {
	BaseHP=-242+26.85*LVL+0.15*(LVL*LVL)
	CPMOD=0.5
	ClassType="F2"}
else if (JOB=="SW" && LVL>40) {
	BaseHP=-767.6+37.59*LVL+0.21*(LVL*LVL)
	CPMOD=0.5
	ClassType="F3"}
else if (JOB=="EFS" && LVL<=20) {
	BaseHP=76.4+12.53*LVL+0.07*(LVL*LVL)
	CPMOD=0.4
	ClassType="F1"}
else if (JOB=="EFS" && LVL>20) {
	BaseHP=-202.2+25.06*LVL+0.14*(LVL*LVL)
	CPMOD=0.4
	ClassType="F2"}
else if (JOB=="PW" && LVL<=20) {
	BaseHP=76.4+12.53*LVL+0.07*(LVL*LVL)
	CPMOD=0.4
	ClassType="F1"}
else if (JOB=="PW" && LVL>20 && LVL<=40) {
	BaseHP=-202.2+25.06*LVL+0.14*(LVL*LVL)
	CPMOD=0.4
	ClassType="F2"}
else if (JOB=="PW" && LVL>40) {
	BaseHP=-552.6+32.22*LVL+0.18*(LVL*LVL)
	CPMOD=0.4
	ClassType="F3"}
else if (JOB=="SR" && LVL<=20) {
	BaseHP=76.4+12.53*LVL+0.07*(LVL*LVL)
	CPMOD=0.5
	ClassType="F1"}
else if (JOB=="SR" && LVL>20 && LVL<=40) {
	BaseHP=-202.2+25.06*LVL+0.14*(LVL*LVL)
	CPMOD=0.5
	ClassType="F2"}
else if (JOB=="SR" && LVL>40) {
	BaseHP=-640.2+34.01*LVL+0.19*(LVL*LVL)
	CPMOD=0.5
	ClassType="F3"}
else if (JOB=="EM") {
	BaseHP=88.7+15.215*LVL+0.085*(LVL*LVL)
	CPMOD=0.5
	ClassType="M1"}
else if (JOB=="EW" && LVL<=20) {
	BaseHP=88.7+15.215*LVL+0.085*(LVL*LVL)
	CPMOD=0.5
	ClassType="M1"}
else if (JOB=="EW" && LVL>20) {
	BaseHP=-90.4+23.27*LVL+0.13*(LVL*LVL)
	CPMOD=0.5
	ClassType="M2"}
else if (JOB=="SPS" && LVL<=20) {
	BaseHP=88.7+15.215*LVL+0.085*(LVL*LVL)
	CPMOD=0.5
	ClassType="M1"}
else if (JOB=="SPS" && LVL>20 && LVL<=40) {
	BaseHP=-90.4+23.27*LVL+0.13*(LVL*LVL)
	CPMOD=0.5
	ClassType="M2"}
else if (JOB=="SPS" && LVL>40) {
	BaseHP=-572.2+33.115*LVL+0.185*(LVL*LVL)
	CPMOD=0.5
	ClassType="M3"}
else if (JOB=="ELS" && LVL<=20) {
	BaseHP=88.7+15.215*LVL+0.085*(LVL*LVL)
	CPMOD=0.6
	ClassType="M1"}
else if (JOB=="ELS" && LVL>20 && LVL<=40) {
	BaseHP=-90.4+23.27*LVL+0.13*(LVL*LVL)
	CPMOD=0.6
	ClassType="M2"}
else if (JOB=="ELS" && LVL>40) {
	BaseHP=-659.8+34.905*LVL+0.195*(LVL*LVL)
	CPMOD=0.6
	ClassType="M3"}
else if (JOB=="EO" && LVL<=20) {
	BaseHP=88.7+15.215*LVL+0.085*(LVL*LVL)
	CPMOD=0.5
	ClassType="M1"}
else if (JOB=="EO" && LVL>20) {
	BaseHP=-209.8+28.64*LVL+0.16*(LVL*LVL)
	CPMOD=0.5
	ClassType="M2"}
else if (JOB=="EE" && LVL<=20) {
	BaseHP=88.7+15.215*LVL+0.085*(LVL*LVL)
	CPMOD=0.5
	ClassType="M1"}
else if (JOB=="EE" && LVL>20 && LVL<=40) {
	BaseHP=-209.8+28.64*LVL+0.16*(LVL*LVL)
	CPMOD=0.5
	ClassType="M2"}
else if (JOB=="EE" && LVL>40) {
	BaseHP=-647.8+37.59*LVL+0.21*(LVL*LVL)
	CPMOD=0.5
	ClassType="M3"}
else if (JOB=="DF") {
	BaseHP=80.5+13.425*LVL+0.075*(LVL*LVL)
	CPMOD=0.4
	ClassType="F1"}
else if (JOB=="PK" && LVL<=20) {
	BaseHP=80.5+13.425*LVL+0.075*(LVL*LVL)
	CPMOD=0.5
	ClassType="F1"}
else if (JOB=="PK" && LVL>20) {
	BaseHP=-257.8+28.64*LVL+0.16*(LVL*LVL)
	CPMOD=0.5
	ClassType="F2"}
else if (JOB=="SK" && LVL<=20) {
	BaseHP=80.5+13.425*LVL+0.075*(LVL*LVL)
	CPMOD=0.6
	ClassType="F1"}
else if (JOB=="SK" && LVL>20 && LVL<=40) {
	BaseHP=-257.8+28.64*LVL+0.16*(LVL*LVL)
	CPMOD=0.6
	ClassType="F2"}
else if (JOB=="SK" && LVL>40) {
	BaseHP=-695.8+37.59*LVL+0.21*(LVL*LVL)
	CPMOD=0.6
	ClassType="F3"}
else if (JOB=="BD" && LVL<=20) {
	BaseHP=80.5+13.425*LVL+0.075*(LVL*LVL)
	CPMOD=0.5
	ClassType="F1"}
else if (JOB=="BD" && LVL>20 && LVL<=40) {
	BaseHP=-257.8+28.64*LVL+0.16*(LVL*LVL)
	CPMOD=0.5
	ClassType="F2"}
else if (JOB=="BD" && LVL>40) {
	BaseHP=-827.2+40.275*LVL+0.225*(LVL*LVL)
	CPMOD=0.5
	ClassType="F3"}
else if (JOB=="AS" && LVL<=20) {
	BaseHP=80.5+13.425*LVL+0.075*(LVL*LVL)
	CPMOD=0.4
	ClassType="F1"}
else if (JOB=="AS" && LVL>20) {
	BaseHP=-218+26.85*LVL+0.15*(LVL*LVL)
	CPMOD=0.4
	ClassType="F2"}
else if (JOB=="AW" && LVL<=20) {
	BaseHP=80.5+13.425*LVL+0.075*(LVL*LVL)
	CPMOD=0.4
	ClassType="F1"}
else if (JOB=="AW" && LVL>20 && LVL<=40) {
	BaseHP=-218+26.85*LVL+0.15*(LVL*LVL)
	CPMOD=0.4
	ClassType="F2"}
else if (JOB=="AW" && LVL>40) {
	BaseHP=-568.4+34.01*LVL+0.19*(LVL*LVL)
	CPMOD=0.4
	ClassType="F3"}
else if (JOB=="PR" && LVL<=20) {
	BaseHP=80.5+13.425*LVL+0.075*(LVL*LVL)
	CPMOD=0.5
	ClassType="F1"}
else if (JOB=="PR" && LVL>20 && LVL<=40) {
	BaseHP=-218+26.85*LVL+0.15*(LVL*LVL)
	CPMOD=0.5
	ClassType="F2"}
else if (JOB=="PR" && LVL>40) {
	BaseHP=-656+35.8*LVL+0.2*(LVL*LVL)
	CPMOD=0.5
	ClassType="F3"}
else if (JOB=="DM") {
	BaseHP=90.7+15.215*LVL+0.085*(LVL*LVL)
	CPMOD=0.5
	ClassType="M1"}
else if (JOB=="DW" && LVL<=20) {
	BaseHP=90.7+15.215*LVL+0.085*(LVL*LVL)
	CPMOD=0.5
	ClassType="M1"}
else if (JOB=="DW" && LVL>20) {
	BaseHP=-108.3+24.165*LVL+0.135*(LVL*LVL)
	CPMOD=0.5
	ClassType="M2"}
else if (JOB=="SPH" && LVL<=20) {
	BaseHP=90.7+15.215*LVL+0.085*(LVL*LVL)
	CPMOD=0.5
	ClassType="M1"}
else if (JOB=="SPH" && LVL>20 && LVL<=40) {
	BaseHP=-108.3+24.165*LVL+0.135*(LVL*LVL)
	CPMOD=0.5
	ClassType="M2"}
else if (JOB=="SPH" && LVL>40) {
	BaseHP=-546.3+33.115*LVL+0.185*(LVL*LVL)
	CPMOD=0.5
	ClassType="M3"}
else if (JOB=="PS" && LVL<=20) {
	BaseHP=90.7+15.215*LVL+0.085*(LVL*LVL)
	CPMOD=0.6
	ClassType="M1"}
else if (JOB=="PS" && LVL>20 && LVL<=40) {
	BaseHP=-108.3+24.165*LVL+0.135*(LVL*LVL)
	CPMOD=0.6
	ClassType="M2"}
else if (JOB=="PS" && LVL>40) {
	BaseHP=-656+35.8*LVL+0.2*(LVL*LVL)
	CPMOD=0.6
	ClassType="M3"}
else if (JOB=="SO" && LVL<=20) {
	BaseHP=90.7+15.215*LVL+0.085*(LVL*LVL)
	CPMOD=0.5
	ClassType="M1"}
else if (JOB=="SO" && LVL>20) {
	BaseHP=-227.7+29.535*LVL+0.165*(LVL*LVL)
	CPMOD=0.5
	ClassType="M2"}
else if (JOB=="SHE" && LVL<=20) {
	BaseHP=90.7+15.215*LVL+0.085*(LVL*LVL)
	CPMOD=0.5
	ClassType="M1"}
else if (JOB=="SHE" && LVL>20 && LVL<=40) {
	BaseHP=-227.7+29.535*LVL+0.165*(LVL*LVL)
	CPMOD=0.5
	ClassType="M2"}
else if (JOB=="SHE" && LVL>40) {
	BaseHP=-621.9+37.59*LVL+0.21*(LVL*LVL)
	CPMOD=0.5
	ClassType="M3"}
else if (JOB=="OF") {
	BaseHP=67.4+12.53*LVL+0.07*(LVL*LVL)
	CPMOD=0.5
	ClassType="F1"}
else if (JOB=="MO" && LVL<=20) {
	BaseHP=67.4+12.53*LVL+0.07*(LVL*LVL)
	CPMOD=0.5
	ClassType="F1"}
else if (JOB=="MO" && LVL>20) {
	BaseHP=-251+26.85*LVL+0.15*(LVL*LVL)
	CPMOD=0.5
	ClassType="F2"}
else if (JOB=="TY" && LVL<=20) {
	BaseHP=67.4+12.53*LVL+0.07*(LVL*LVL)
	CPMOD=0.5
	ClassType="F1"}
else if (JOB=="TY" && LVL>20 && LVL<=40) {
	BaseHP=-251+26.85*LVL+0.15*(LVL*LVL)
	CPMOD=0.5
	ClassType="F2"}
else if (JOB=="TY" && LVL>40) {
	BaseHP=-776.6+37.59*LVL+0.21*(LVL*LVL)
	CPMOD=0.5
	ClassType="F3"}
else if (JOB=="OR" && LVL<=20) {
	BaseHP=67.4+12.53*LVL+0.07*(LVL*LVL)
	CPMOD=0.7
	ClassType="F1"}
else if (JOB=="OR" && LVL>20) {
	BaseHP=-290.8+28.64*LVL+0.16*(LVL*LVL)
	CPMOD=0.7
	ClassType="F2"}
else if (JOB=="DE" && LVL<=20) {
	BaseHP=67.4+12.53*LVL+0.07*(LVL*LVL)
	CPMOD=0.7
	ClassType="F1"}
else if (JOB=="DE" && LVL>20 && LVL<=40) {
	BaseHP=-290.8+28.64*LVL+0.16*(LVL*LVL)
	CPMOD=0.7
	ClassType="F2"}
else if (JOB=="DE" && LVL>40) {
	BaseHP=-816.4+39.38*LVL+0.22*(LVL*LVL)
	CPMOD=0.7
	ClassType="F3"}
else if (JOB=="OM") {
	BaseHP=79.7+15.215*LVL+0.085*(LVL*LVL)
	CPMOD=0.5
	ClassType="M1"}
else if (JOB=="OS" && LVL<=20) {
	BaseHP=79.7+15.215*LVL+0.085*(LVL*LVL)
	CPMOD=0.5
	ClassType="M1"}
else if (JOB=="OS" && LVL>20) {
	BaseHP=-218.8+28.64*LVL+0.16*(LVL*LVL)
	CPMOD=0.5
	ClassType="M2"}
else if (JOB=="WC" && LVL<=20) {
	BaseHP=79.7+15.215*LVL+0.085*(LVL*LVL)
	CPMOD=0.5
	ClassType="M1"}
else if (JOB=="WC" && LVL>20 && LVL<=40) {
	BaseHP=-218.8+28.64*LVL+0.16*(LVL*LVL)
	CPMOD=0.5
	ClassType="M2"}
else if (JOB=="WC" && LVL>40) {
	BaseHP=-613+36.695*LVL+0.205*(LVL*LVL)
	CPMOD=0.5
	ClassType="M3"}
else if (JOB=="OL" && LVL<=20) {
	BaseHP=79.7+15.215*LVL+0.085*(LVL*LVL)
	CPMOD=0.8
	ClassType="M1"}
else if (JOB=="OL" && LVL>20 && LVL<=40) {
	BaseHP=-218.8+28.64*LVL+0.16*(LVL*LVL)
	CPMOD=0.8
	ClassType="M2"}
else if (JOB=="OL" && LVL>40) {
	BaseHP=-613+36.695*LVL+0.205*(LVL*LVL)
	CPMOD=0.8
	ClassType="M3"}
else if (JOB=="DO") {
	BaseHP=67.4+12.53*LVL+0.07*(LVL*LVL)
	CPMOD=0.7
	ClassType="F1"}
else if (JOB=="AR" && LVL<=20) {
	BaseHP=67.4+12.53*LVL+0.07*(LVL*LVL)
	CPMOD=0.8
	ClassType="F1"}
else if (JOB=="AR" && LVL>20) {
	BaseHP=-251+26.85*LVL+0.15*(LVL*LVL)
	CPMOD=0.8
	ClassType="F2"}
else if (JOB=="WS" && LVL<=20) {
	BaseHP=67.4+12.53*LVL+0.07*(LVL*LVL)
	CPMOD=0.8
	ClassType="F1"}
else if (JOB=="WS" && LVL>20 && LVL<=40) {
	BaseHP=-251+26.85*LVL+0.15*(LVL*LVL)
	CPMOD=0.8
	ClassType="F2"}
else if (JOB=="WS" && LVL>40) {
	BaseHP=-776.6+37.59*LVL+0.21*(LVL*LVL)
	CPMOD=0.8
	ClassType="F3"}
else if (JOB=="SC" && LVL<=20) {
	BaseHP=67.4+12.53*LVL+0.07*(LVL*LVL)
	CPMOD=0.7
	ClassType="F1"}
else if (JOB=="SC" && LVL>20) {
	BaseHP=-290.8+28.64*LVL+0.16*(LVL*LVL)
	CPMOD=0.7
	ClassType="F2"}
else if (JOB=="BH" && LVL<=20) {
	BaseHP=67.4+12.53*LVL+0.07*(LVL*LVL)
	CPMOD=0.7
	ClassType="F1"}
else if (JOB=="BH" && LVL>20 && LVL<=40) {
	BaseHP=-290.8+28.64*LVL+0.16*(LVL*LVL)
	CPMOD=0.7
	ClassType="F2"}
else if (JOB=="BH" && LVL>40) {
	BaseHP=-816.4+39.38*LVL+0.22*(LVL*LVL)
	CPMOD=0.7
	ClassType="F3"}
BaseCP=BaseHP*CPMOD

//Base MP
if (ClassType=="F1")
	{BaseMP=FIGHTER1MP[LVL]}
else if (ClassType=="F2")
	{BaseMP=FIGHTER2MP[LVL]}
else if (ClassType=="F3")
	{BaseMP=FIGHTER3MP[LVL]}
else if (ClassType=="M1")
	{BaseMP=MYSTIC1MP[LVL]}
else if (ClassType=="M2")
	{BaseMP=MYSTIC2MP[LVL]}
else if (ClassType=="M3")
	{BaseMP=MYSTIC3MP[LVL]}

//Move Mode
var movemode=d.gI("MVM").value
movemode=MOVEMODE[movemode]

//Shield Grade
var shieldgrade=d.gI("SHDG").value
shieldgrade=GRADE[shieldgrade]
if (shieldgrade=="S")
	{shield=d.gI("SHDS").value;}
else if (shieldgrade=="A")
	{shield=d.gI("SHDA").value;}
else if (shieldgrade=="B")
	{shield=d.gI("SHDB").value;}
else if (shieldgrade=="C")
	{shield=d.gI("SHDC").value;}
else if (shieldgrade=="D")
	{shield=d.gI("SHDD").value;}
else if (shieldgrade=="N")
	{shield=d.gI("SHDN").value;}

//Shields
Shield=SHIELDPDEF[shield]
if (shield=="0")
{ShieldEvasion=0}
else if (shield>0)
{ShieldEvasion=-8}

//Shield Enchant
var shieldenchant=d.gI("SHDE").value
if (shieldenchant <= 3)
	{shieldunderenchant=shieldenchant;shieldoverenchant=0;}
else
	{shieldunderenchant=3;shieldoverenchant=shieldenchant-3;}
if (shield=="0") {
	shieldunderPDEF=0
	shieldoverPDEF=0
	Shield=Shield
	BaseShieldBlockRate=0
	}
else if (shieldgrade=="N") {
	shieldunderPDEF=0
	shieldoverPDEF=0
	Shield=Shield
	}
else {
	shieldunderPDEF=1
	shieldoverPDEF=3
	Shield=Shield+(shieldunderenchant*shieldunderPDEF)+(shieldoverenchant*shieldoverPDEF)
	}

//Weapon Grade
var weapongrade=d.gI("WPNG").value
weapongrade=GRADE[weapongrade]
if (weapongrade=="S")
	{var weapon=d.gI("WPNS").value}
else if (weapongrade=="A")
	{var weapon=d.gI("WPNA").value}
else if (weapongrade=="B")
	{var weapon=d.gI("WPNB").value}
else if (weapongrade=="C")
	{var weapon=d.gI("WPNC").value}
else if (weapongrade=="D")
	{var weapon=d.gI("WPND").value}
else if (weapongrade=="N")
	{var weapon=d.gI("WPNN").value}
else if (weapongrade=="H")
	{var weapon=d.gI("WPNH").value}
else if (weapongrade=="S80")
	{var weapon=d.gI("WPN8").value}

//Weapon SAs
var weaponsa=document.forms['statcalculator'].WPNSA;
weaponsa.length=5
for (i=0;i<5;i++)
	{
	var p=i*2+4
	if (WEAPON[weapon][p]!="")
		{
		weaponsa.options[i] = new Option(WEAPON[weapon][p],WEAPON[weapon][p],false,weaponsa.options[i].selected);
		}
	if (WEAPON[weapon][p]==""||WEAPON[weapon][p]==null)
		{
		weaponsa.length=i
		break
		}
	}

//Weapon Enchant
var weaponenchant=d.gI("WPNE").value
if (weaponenchant <= 3)
	{weaponunderenchant=weaponenchant;weaponoverenchant=0;}
else
	{weaponunderenchant=3;weaponoverenchant=weaponenchant-3;}
if (weapongrade=="N"||weapon=="0") {
	d.gI("WPNE").disabled=true
	underMATK=0
	overMATK=0
	}
else if (weapongrade=="H") {
	d.gI("WPNE").disabled=true
	underMATK=0
	overMATK=0
	}
else if (weapongrade=="D") {
	d.gI("WPNE").disabled=false
	underMATK=2
	overMATK=4
	}
else if (weapongrade=="S"||weapongrade=="S80") {
	d.gI("WPNE").disabled=false
	underMATK=4
	overMATK=8
	}
else {
	d.gI("WPNE").disabled=false
	underMATK=3
	overMATK=6
	}

///////Y
//Weapons
WpnPATK=WEAPON[weapon][0]
WpnMATK=WEAPON[weapon][1]
WpnType=WEAPON[weapon][2]
if (WpnType!="Duals")
	{
	var weapon_sa=d.gI("WPNSA").value
	var sasubvalue=d.gI("WPNSA").selectedIndex
	var samultiplier=sasubvalue*2+5
	}
else if (WpnType=="Duals")
	{
	var duals_sa=WEAPON[weapon][4]
	var duals_v=WEAPON[weapon][5]
	}

var CSA=0
if (weapongrade=="D" && LVL<20 && weapon!=0)
	{BuffACC=BuffACC-20;d.gI("weapon grade penalty").innerHTML=GPA}
else if (weapongrade=="C" && LVL<40 && weapon!=0)
	{CSA=1;BuffACC=BuffACC-20;d.gI("weapon grade penalty").innerHTML=GPA}
else if (weapongrade=="B" && LVL<52 && weapon!=0)
	{CSA=1;BuffACC=BuffACC-20;d.gI("weapon grade penalty").innerHTML=GPA}
else if (weapongrade=="A" && LVL<61 && weapon!=0)
	{CSA=1;BuffACC=BuffACC-20;d.gI("weapon grade penalty").innerHTML=GPA}
else if ((weapongrade=="S"||weapongrade=="H") && LVL<76 && weapon!=0)
	{CSA=1;BuffACC=BuffACC-20;d.gI("weapon grade penalty").innerHTML=GPA}
else if (weapongrade=="S80" && LVL<76 && weapon!=0)
	{CSA=1;BuffACC=BuffACC-20;d.gI("weapon grade penalty").innerHTML=GPA}
else if (weapongrade=="S80" && weapon_sa!="no SA" && LVL<80 && weapon!=0 && WpnType!="Duals")
	{CSA=1;weapon_sa="no SA";d.gI("weapon grade penalty").innerHTML=GPA}
else if (weapongrade=="S80" && LVL<80 && weapon!=0 && WpnType=="Duals" && weaponenchant>=4)
	{CSA=1;weapon_sa="no SA";d.gI("weapon grade penalty").innerHTML=GPA}
else
	{d.gI("weapon grade penalty").innerHTML=" "}

if ((race=="HM"||race=="EM"||race=="DM"||race=="OM") && weapon==0)
	{WpnPATK=3}
if (weapon_sa=="Haste" && CSA==0)
	{BuffASPD=BuffASPD*WEAPON[weapon][samultiplier]}
else if (weapon_sa=="Acumen" && CSA==0)
	{BuffCAST=BuffCAST*1.15}
else if (weapon_sa=="Mana Up" && CSA==0)
	{BuffMP=BuffMP*1.3}
else if (weapon_sa=="Health" && CSA==0)
	{BuffHP=BuffHP*1.25}
else if (weapon_sa=="Focus" && CSA==0)
	{AddCRIT=AddCRIT+WEAPON[weapon][samultiplier]}
else if (weapon_sa=="Empower" && CSA==0)
	{AddMATK=AddMATK+WEAPON[weapon][samultiplier]}
else if (weapon_sa=="Evasion" && CSA==0)
	{BuffEVA=BuffEVA+WEAPON[weapon][samultiplier]}
else if (weapon_sa=="Guidance" && CSA==0)
	{BuffACC=BuffACC+WEAPON[weapon][samultiplier]}
else if (weapon_sa=="Anger" && CSA==0)
	{BuffHP=BuffHP*0.85;AddPATK=AddPATK+WEAPON[weapon][samultiplier]}
else if (weapon_sa=="Conversion" && CSA==0)
	{BuffMP=BuffMP*1.6;BuffHP=BuffHP*0.6}
else if (weapon_sa=="Rsk. Focus" && CSA==0)
	{AddCRIT60=AddCRIT60+WEAPON[weapon][samultiplier]}
else if (weapon_sa=="Rsk. Haste" && CSA==0)
	{BuffASPD60=BuffASPD60*WEAPON[weapon][samultiplier]}
else if (weapon_sa=="Rsk. Evasion" && CSA==0)
	{BuffEVA60=BuffEVA60+WEAPON[weapon][samultiplier]}
else if (weapon_sa=="Magic Power" && CSA==0)
	{AddMATK=AddMATK+WEAPON[weapon][samultiplier]}
else if (weapon_sa=="Critical Anger" && CSA==0)
	{BuffHP=BuffHP*0.85;AddCRITDMG=AddCRITDMG+WEAPON[weapon][samultiplier]}
else if (weapon_sa=="Critical Damage" && CSA==0)
	{AddCRITDMG=AddCRITDMG+WEAPON[weapon][samultiplier]}
else if (weapon_sa=="Quick Recovery" && CSA==0)
	{bowdelay=bowdelay*WEAPON[weapon][samultiplier]}
else if (weapon_sa=="Back Blow" && CSA==0 && (position==3||position==5))
	{AddCOMBATCRIT=AddCOMBATCRIT+(WEAPON[weapon][samultiplier]*1.4)}
else if (weapon_sa=="HP Regeneration" && CSA==0)
	{AddHPR=AddHPR+(WEAPON[weapon][samultiplier]*movemode)}
else if (weapon_sa=="MP Regeneration" && CSA==0)
	{AddMPR=AddMPR+(WEAPON[weapon][samultiplier]*movemode)}
else if (weapon_sa=="HP Drain" && CSA==0)
	{BuffVR=BuffVR+WEAPON[weapon][samultiplier]}
else if (duals_sa=="HP/CP/MP" && weaponenchant >=4 && CSA==0)
	{BuffHP=BuffHP*1.15;BuffMP=BuffMP*1.2;BuffCP=BuffCP*1.3}
else if (duals_sa=="Guidance" && weaponenchant >=4 && CSA==0)
	{BuffACC=BuffACC+duals_v}
else if (duals_sa=="Health" && weaponenchant >=4 && CSA==0)
	{BuffHP=BuffHP*1.25}
else if (duals_sa=="Haste" && weaponenchant >=4 && CSA==0)
	{BuffASPD=BuffASPD*duals_v}
else if (duals_sa=="Focus" && weaponenchant >=4 && CSA==0)
	{AddCRIT=AddCRIT+duals_v}
else if (duals_sa=="Critical Damage" && weaponenchant >=4 && CSA==0)
	{AddCRITDMG=AddCRITDMG+duals_v}
else if (weapon=="330" && CSA==0)
	{BuffHP=BuffHP*1.25;BuffMP=BuffMP*1.3;BuffCP=BuffCP*1.5;BuffShieldBlockRate=BuffShieldBlockRate*1.39;BuffRD=BuffRD+0.09}
else if (weapon=="331" && CSA==0)
	{BuffHP=BuffHP*1.25;BuffMP=BuffMP*1.3;BuffCP=BuffCP*1.5;BuffRD=BuffRD+0.09;BuffSHIELDPDEF=BuffSHIELDPDEF*1.33}
else if (weapon=="332" && CSA==0)
	{BuffMP=BuffMP*1.3;BuffCP=BuffCP*1.5;bowdelay=bowdelay*0.932}
else if (weapon=="333" && CSA==0)
	{BuffHP=BuffHP*1.25;BuffCP=BuffCP*1.5;AddCRIT=AddCRIT+78.7;AddCRITDMG=AddCRITDMG+504}
else if (weapon=="334" && CSA==0)
	{BuffHP=BuffHP*1.25;BuffCP=BuffCP*1.5;BuffASPD=BuffASPD*1.07}
else if (weapon=="335" && CSA==0)
	{BuffHP=BuffHP*1.25;BuffMP=BuffMP*1.3;BuffCP=BuffCP*1.5;BuffEVA=BuffEVA+3.15}
else if (weapon=="336" && CSA==0)
	{BuffMP=BuffMP*1.3;BuffCP=BuffCP*1.5;BuffCAST=BuffCAST*1.15}
else if (weapon=="337" && CSA==0)
	{BuffMP=BuffMP*1.3;BuffCP=BuffCP*1.5;AddMATK=AddMATK+29.67}
else if (weapon=="338" && CSA==0)
	{BuffHP=BuffHP*1.25;BuffCP=BuffCP*1.5;BuffASPD=BuffASPD*1.07;BuffACC=BuffACC+4.89}
else if (weapon=="339" && CSA==0)
	{BuffMP=BuffMP*1.3;BuffCP=BuffCP*1.5;BuffASPD*BuffASPD*1.03;BuffVR=BuffVR+0.02}
else if (weapon=="340" && CSA==0)
	{BuffHP=BuffHP*1.25;BuffMP=BuffMP*1.3;BuffCP=BuffCP*1.5;AddCRIT=AddCRIT+78.7}
if (weapon=="339" && CSA==0 && (position==3||position==5))
	{AddCOMBATCRIT=AddCOMBATCRIT+22}

//Weapon Types
var ShieldDisabled=false
if (WpnType=="unequipped")
	{WpnSpd=300;WpnAcc=0;WpnCrt=40;WpnMAS="Fist";ShieldDisabled=false;randdmg=1.1;minranddmg=0.9}
else if (WpnType=="2hs")
	{WpnSpd=325;WpnAcc=0;WpnCrt=80;WpnMAS="Sword";ShieldDisabled=true;randdmg=1.1;minranddmg=0.9}
else if (WpnType=="2hb")
	{WpnSpd=325;WpnAcc=4.75;WpnCrt=40;WpnMAS="Blunt";ShieldDisabled=true;randdmg=1.2;minranddmg=0.8}
else if (WpnType=="Sword")
	{WpnSpd=379;WpnAcc=0;WpnCrt=80;WpnMAS="Sword";ShieldDisabled=false;randdmg=1.1;minranddmg=0.9}
else if (WpnType=="Duals")
	{WpnSpd=325;WpnAcc=0;WpnCrt=80;WpnMAS="Duals";ShieldDisabled=true;randdmg=1.1;minranddmg=0.9}
else if (WpnType=="Blunt")
	{WpnSpd=379;WpnAcc=4.75;WpnCrt=40;WpnMAS="Blunt";ShieldDisabled=false;randdmg=1.2;minranddmg=0.8}
else if (WpnType=="Bow")
	{WpnSpd=293;WpnAcc=(-3.75);WpnCrt=120;WpnMAS="Bow";ShieldDisabled=true;randdmg=1.05;minranddmg=0.95}
else if (WpnType=="YumiBow")
	{WpnSpd=227;WpnAcc=(-3.75);WpnCrt=120;WpnMAS="Bow";ShieldDisabled=true;randdmg=1.05;minranddmg=0.95;bowdelay=bowdelay*0.5466}
else if (WpnType=="Dagger")
	{WpnSpd=433;WpnAcc=(-3.75);WpnCrt=120;WpnMAS="Dagger";ShieldDisabled=false;randdmg=1.05;minranddmg=0.95}
else if (WpnType=="Polearm")
	{WpnSpd=325;WpnAcc=(-3.75);WpnCrt=80;WpnMAS="Polearm";ShieldDisabled=true;randdmg=1.1;minranddmg=0.9}
else if (WpnType=="Fist")
	{WpnSpd=325;WpnAcc=4.75;WpnCrt=40;WpnMAS="Fist";ShieldDisabled=true;randdmg=1.05;minranddmg=0.95}
else if (WpnType=="Etc")
	{WpnSpd=379;WpnAcc=4.75;WpnCrt=40;WpnMAS="Etc";ShieldDisabled=false;randdmg=1.1;minranddmg=0.9}

if (ShieldDisabled==true) {
	d.gI("SHDS").disabled=true
	d.gI("SHDA").disabled=true
	d.gI("SHDB").disabled=true
	d.gI("SHDC").disabled=true
	d.gI("SHDD").disabled=true
	d.gI("SHDN").disabled=true
	shield="unequipped"
	Shield=0
	ShieldEvasion=0
	BaseShieldBlockRate=0
	}
else if (ShieldDisabled==false) {
	d.gI("SHDS").disabled=false
	d.gI("SHDA").disabled=false
	d.gI("SHDB").disabled=false
	d.gI("SHDC").disabled=false
	d.gI("SHDD").disabled=false
	d.gI("SHDN").disabled=false
}
if (shieldgrade=="N"||Shield=="0") {
	d.gI("SHDE").disabled=true
	}
else {
	d.gI("SHDE").disabled=false
	}

if (shieldgrade=="D" && LVL<20 && Shield!=0)
	{pen=pen+1;d.gI("SHD grade penalty").innerHTML=GPA}
else if (shieldgrade=="C" && LVL<40 && Shield!=0)
	{pen=pen+1;d.gI("SHD grade penalty").innerHTML=GPA}
else if (shieldgrade=="B" && LVL<52 && Shield!=0)
	{pen=pen+1;d.gI("SHD grade penalty").innerHTML=GPA}
else if (shieldgrade=="A" && LVL<61 && Shield!=0)
	{pen=pen+1;d.gI("SHD grade penalty").innerHTML=GPA}
else if (shieldgrade=="S" && LVL<76 && Shield!=0)
	{pen=pen+1;d.gI("SHD grade penalty").innerHTML=GPA}
else
	{d.gI("SHD grade penalty").innerHTML=" "}


if (WpnMAS=="Bow" && weapongrade=="S"||weapongrade=="S80")
	{underPATK=10;overPATK=20}
else if (WpnMAS=="Bow" && weapongrade=="A")
	{underPATK=8;overPATK=16}
else if (WpnMAS=="Bow" && (weapongrade=="B" || weapongrade=="C"))
	{underPATK=6;overPATK=12}
else if (WpnMAS=="Bow" && weapongrade=="D")
	{underPATK=4;overPATK=8}
else if (WpnType=="Dagger" && weapongrade=="S"||weapongrade=="S80")
	{underPATK=5;overPATK=10}
else if (WpnType=="Dagger" && weapongrade=="A")
	{underPATK=4;overPATK=8}
else if (WpnType=="Dagger" && (weapongrade=="B" || weapongrade=="C"))
	{underPATK=3;overPATK=6}
else if (WpnType=="Dagger" && weapongrade=="D")
	{underPATK=2;overPATK=4}
else if ((WpnType=="2hs"||WpnType=="2hb") && weapongrade=="S"||weapongrade=="S80")
	{underPATK=6;overPATK=12}
else if ((WpnType=="2hs"||WpnType=="2hb") && weapongrade=="A")
	{underPATK=5;overPATK=10}
else if ((WpnType=="2hs"||WpnType=="2hb") && (weapongrade=="B" || weapongrade=="C"))
	{underPATK=4;overPATK=8}
else if ((WpnType=="2hs"||WpnType=="2hb") && weapongrade=="D")
	{underPATK=2;overPATK=4}
else if ((WpnType=="Duals"||WpnType=="Fist") && weapongrade=="S"||weapongrade=="S80")
	{underPATK=6;overPATK=12}
else if ((WpnType=="Duals"||WpnType=="Fist") && weapongrade=="A")
	{underPATK=5;overPATK=10}
else if ((WpnType=="Duals"||WpnType=="Fist") && (weapongrade=="B" || weapongrade=="C"))
	{underPATK=4;overPATK=8}
else if ((WpnType=="Duals"||WpnType=="Fist") && weapongrade=="D")
	{underPATK=4;overPATK=8}
else if ((WpnType=="Sword"||WpnType=="Blunt") && weapongrade=="S"||weapongrade=="S80")
	{underPATK=5;overPATK=10}
else if ((WpnType=="Sword"||WpnType=="Blunt") && weapongrade=="A")
	{underPATK=4;overPATK=8}
else if ((WpnType=="Sword"||WpnType=="Blunt") && (weapongrade=="B" || weapongrade=="C"))
	{underPATK=3;overPATK=6}
else if ((WpnType=="Sword"||WpnType=="Blunt") && weapongrade=="D")
	{underPATK=2;overPATK=4}
else if (WpnType=="Polearm" && weapongrade=="S"||weapongrade=="S80")
	{underPATK=5;overPATK=10}
else if (WpnType=="Polearm" && weapongrade=="A")
	{underPATK=4;overPATK=8}
else if (WpnType=="Polearm" && (weapongrade=="B" || weapongrade=="C"))
	{underPATK=3;overPATK=6}
else if (WpnType=="Polearm" && weapongrade=="D")
	{underPATK=2;overPATK=4}

///////O
//Helmet Grade
var helmetgrade=d.gI("HLMG").value
helmetgrade=GRADE[helmetgrade]
if (helmetgrade=="S")
	{helmet=d.gI("HLMS").value}
else if (helmetgrade=="A")
	{helmet=d.gI("HLMA").value}
else if (helmetgrade=="B")
	{helmet=d.gI("HLMB").value}
else if (helmetgrade=="C")
	{helmet=d.gI("HLMC").value}
else if (helmetgrade=="D")
	{helmet=d.gI("HLMD").value}
else if (helmetgrade=="N")
	{helmet=d.gI("HLMN").value}

if (helmetgrade=="D" && LVL<20 && helmet!=0)
	{pen=pen+1;d.gI("HLM grade penalty").innerHTML=GPA}
else if (helmetgrade=="C" && LVL<40 && helmet!=0)
	{pen=pen+1;d.gI("HLM grade penalty").innerHTML=GPA}
else if (helmetgrade=="B" && LVL<52 && helmet!=0)
	{pen=pen+1;d.gI("HLM grade penalty").innerHTML=GPA}
else if (helmetgrade=="A" && LVL<61 && helmet!=0)
	{pen=pen+1;d.gI("HLM grade penalty").innerHTML=GPA}
else if (helmetgrade=="S" && LVL<76 && helmet!=0)
	{pen=pen+1;d.gI("HLM grade penalty").innerHTML=GPA}
else
	{d.gI("HLM grade penalty").innerHTML=" "}

//Helmets
Helmet=HELMETPDEF[helmet]
HelmetSet=HELMETSET[helmet]

//Helmet Enchant
var helmetenchant=d.gI("HLME").value
if (helmetenchant <= 3)
	{helmetunderenchant=helmetenchant;helmetoverenchant=0;}
else
	{helmetunderenchant=3;helmetoverenchant=helmetenchant-3;}
if (helmetgrade=="N"||helmet=="0") {
	d.gI("HLME").disabled=true
	helmetunderPDEF=0
	helmetoverPDEF=0
	Helmet=Helmet
	}
else {
	d.gI("HLME").disabled=false
	helmetunderPDEF=1
	helmetoverPDEF=3
	Helmet=Helmet+(helmetunderenchant*helmetunderPDEF)+(helmetoverenchant*helmetoverPDEF)
	}

//Body Grade
var lowergrade=d.gI("LWRG").value
lowergrade=GRADE[lowergrade]
if (lowergrade=="S")
	{lower=d.gI("LWRS").value}
else if (lowergrade=="A")
	{lower=d.gI("LWRA").value}
else if (lowergrade=="B")
	{lower=d.gI("LWRB").value}
else if (lowergrade=="C")
	{lower=d.gI("LWRC").value}
else if (lowergrade=="D")
	{lower=d.gI("LWRD").value}
else if (lowergrade=="N")
	{lower=d.gI("LWRN").value}

//Body (Lower)
Lower=LOWERPDEF[lower]
ALType=LOWERTYPE[lower]
LowerSet=LOWERSET[lower]
if ((race=="HM"||race=="EM"||race=="DM"||race=="OM") && lower==0)
{Lower=8}

//Body (Lower) Enchant
var lowerenchant=d.gI("LWRE").value
if (lowerenchant <= 3)
	{lowerunderenchant=lowerenchant;loweroverenchant=0;}
else
	{lowerunderenchant=3;loweroverenchant=lowerenchant-3;}
if (lowergrade=="N"||lower=="0") {
	d.gI("LWRE").disabled=true
	lowerunderPDEF=0
	loweroverPDEF=0
	Lower=Lower
	}
else {
	d.gI("LWRE").disabled=false
	lowerunderPDEF=1
	loweroverPDEF=3
	Lower=Lower+(lowerunderenchant*lowerunderPDEF)+(loweroverenchant*loweroverPDEF)
	}

//Body (Upper) Grade
var uppergrade=d.gI("UPRG").value
uppergrade=GRADE[uppergrade]
if (uppergrade=="S")
	{upper=d.gI("UPRS").value}
else if (uppergrade=="A")
	{upper=d.gI("UPRA").value}
else if (uppergrade=="B")
	{upper=d.gI("UPRB").value}
else if (uppergrade=="C")
	{upper=d.gI("UPRC").value}
else if (uppergrade=="D")
	{upper=d.gI("UPRD").value}
else if (uppergrade=="N")
	{upper=d.gI("UPRN").value}

if (uppergrade=="D" && LVL<20 && upper!=0)
	{pen=pen+1;d.gI("UPR grade penalty").innerHTML=GPA}
else if (uppergrade=="C" && LVL<40 && upper!=0)
	{pen=pen+1;d.gI("UPR grade penalty").innerHTML=GPA}
else if (uppergrade=="B" && LVL<52 && upper!=0)
	{pen=pen+1;d.gI("UPR grade penalty").innerHTML=GPA}
else if (uppergrade=="A" && LVL<61 && upper!=0)
	{pen=pen+1;d.gI("UPR grade penalty").innerHTML=GPA}
else if (uppergrade=="S" && LVL<76 && upper!=0)
	{pen=pen+1;d.gI("UPR grade penalty").innerHTML=GPA}
else
	{d.gI("UPR grade penalty").innerHTML=" "}

//Upper
Upper=UPPERPDEF[upper]
AddMP=AddMP+UPPERMP[upper]
AUType=UPPERTYPE[upper]
fullbody=FULLBODY[upper]
UpperSet=UPPERSET[upper]
if ((race=="HM"||race=="EM"||race=="DM"||race=="OM") && upper==0)
	{Upper=15}

//Body (Upper) Enchant
var upperenchant=d.gI("UPRE").value
if (upperenchant <= 3)
	{upperunderenchant=upperenchant;upperoverenchant=0;}
else
	{upperunderenchant=3;upperoverenchant=upperenchant-3;}
if (uppergrade=="N"||upper=="0") {
	d.gI("UPRE").disabled=true
	upperunderPDEF=0
	upperoverPDEF=0
	Upper=Upper
	}
else {
	d.gI("UPRE").disabled=false
	upperunderPDEF=1
	upperoverPDEF=3
	Upper=Upper+(upperunderenchant*upperunderPDEF)+(upperoverenchant*upperoverPDEF)
	}

//Disable Lower when Upper is a full body armor
if (fullbody==1)
	{
	d.gI("LWRS").disabled=true
	d.gI("LWRA").disabled=true
	d.gI("LWRB").disabled=true
	d.gI("LWRC").disabled=true
	d.gI("LWRD").disabled=true
	d.gI("LWRN").disabled=true
	d.gI("LWRE").disabled=true
	lower="unequipped"
	Lower=0
	}
else if (fullbody==0)
	{
	d.gI("LWRS").disabled=false
	d.gI("LWRA").disabled=false
	d.gI("LWRB").disabled=false
	d.gI("LWRC").disabled=false
	d.gI("LWRD").disabled=false
	d.gI("LWRN").disabled=false
	AddMP=AddMP+LOWERMP[lower]
	}

if (lowergrade=="D" && LVL<20 && lower!=0 && fullbody==0)
	{pen=pen+1;d.gI("LWR grade penalty").innerHTML=GPA}
else if (lowergrade=="C" && LVL<40 && lower!=0 && fullbody==0)
	{pen=pen+1;d.gI("LWR grade penalty").innerHTML=GPA}
else if (lowergrade=="B" && LVL<52 && lower!=0 && fullbody==0)
	{pen=pen+1;d.gI("LWR grade penalty").innerHTML=GPA}
else if (lowergrade=="A" && LVL<61 && lower!=0 && fullbody==0)
	{pen=pen+1;d.gI("LWR grade penalty").innerHTML=GPA}
else if (lowergrade=="S" && LVL<76 && lower!=0 && fullbody==0)
	{pen=pen+1;d.gI("LWR grade penalty").innerHTML=GPA}
else
	{d.gI("LWR grade penalty").innerHTML=" "}

//Armor Types (Heavy/Light/Robe)
if (AUType=="H" && (ALType=="H"||fullbody==1))
	{AType="H"}
else if (AUType=="L" && (ALType=="L"||fullbody==1))
	{AType="L"}
else if (AUType=="R" && (ALType=="R"||fullbody==1))
	{AType="R"}

//Glove Grade
var glovegrade=d.gI("GLVG").value
glovegrade=GRADE[glovegrade]
if (glovegrade=="S")
	{glove=d.gI("GLVS").value}
else if (glovegrade=="A")
	{glove=d.gI("GLVA").value}
else if (glovegrade=="B")
	{glove=d.gI("GLVB").value}
else if (glovegrade=="C")
	{glove=d.gI("GLVC").value}
else if (glovegrade=="D")
	{glove=d.gI("GLVD").value}
else if (glovegrade=="N")
	{glove=d.gI("GLVN").value}

if (glovegrade=="D" && LVL<20 && glove!=0)
	{pen=pen+1;d.gI("GLV grade penalty").innerHTML=GPA}
else if (glovegrade=="C" && LVL<40 && glove!=0)
	{pen=pen+1;d.gI("GLV grade penalty").innerHTML=GPA}
else if (glovegrade=="B" && LVL<52 && glove!=0)
	{pen=pen+1;d.gI("GLV grade penalty").innerHTML=GPA}
else if (glovegrade=="A" && LVL<61 && glove!=0)
	{pen=pen+1;d.gI("GLV grade penalty").innerHTML=GPA}
else if (glovegrade=="S" && LVL<76 && glove!=0)
	{pen=pen+1;d.gI("GLV grade penalty").innerHTML=GPA}
else
	{d.gI("GLV grade penalty").innerHTML=" "}

//Glove
Glove=GLOVEPDEF[glove]
GloveSet=GLOVESET[glove]

//Glove Enchant
var gloveenchant=d.gI("GLVE").value
if (gloveenchant <= 3)
	{gloveunderenchant=gloveenchant;gloveoverenchant=0;}
else
	{gloveunderenchant=3;gloveoverenchant=gloveenchant-3;}
if (glovegrade=="N"||glove=="0") {
	d.gI("GLVE").disabled=true
	gloveunderPDEF=0
	gloveoverPDEF=0
	Glove=Glove
	}
else {
	d.gI("GLVE").disabled=false
	gloveunderPDEF=1
	gloveoverPDEF=3
	Glove=Glove+(gloveunderenchant*gloveunderPDEF)+(gloveoverenchant*gloveoverPDEF)
	}

//Boot Grade
var bootgrade=d.gI("BOOG").value
bootgrade=GRADE[bootgrade]
if (bootgrade=="S")
	{boot=d.gI("BOOS").value}
else if (bootgrade=="A")
	{boot=d.gI("BOOA").value}
else if (bootgrade=="B")
	{boot=d.gI("BOOB").value}
else if (bootgrade=="C")
	{boot=d.gI("BOOC").value}
else if (bootgrade=="D")
	{boot=d.gI("BOOD").value}
else if (bootgrade=="N")
	{boot=d.gI("BOON").value}

if (bootgrade=="D" && LVL<20 && boot!=0)
	{pen=pen+1;d.gI("BOO grade penalty").innerHTML=GPA}
else if (bootgrade=="C" && LVL<40 && boot!=0)
	{pen=pen+1;d.gI("BOO grade penalty").innerHTML=GPA}
else if (bootgrade=="B" && LVL<52 && boot!=0)
	{pen=pen+1;d.gI("BOO grade penalty").innerHTML=GPA}
else if (bootgrade=="A" && LVL<61 && boot!=0)
	{pen=pen+1;d.gI("BOO grade penalty").innerHTML=GPA}
else if (bootgrade=="S" && LVL<76 && boot!=0)
	{pen=pen+1;d.gI("BOO grade penalty").innerHTML=GPA}
else
	{d.gI("BOO grade penalty").innerHTML=" "}

//Boot
Boot=BOOTPDEF[boot]
BootSet=BOOTSET[boot]

//Boot Enchant
var bootenchant=d.gI("BOOE").value
if (bootenchant <= 3)
	{bootunderenchant=bootenchant;bootoverenchant=0;}
else
	{bootunderenchant=3;bootoverenchant=bootenchant-3;}
if (bootgrade=="N"||boot=="0") {
	d.gI("BOOE").disabled=true
	bootunderPDEF=0
	bootoverPDEF=0
	Boot=Boot
	}
else {
	d.gI("BOOE").disabled=false
	bootunderPDEF=1
	bootoverPDEF=3
	Boot=Boot+(bootunderenchant*bootunderPDEF)+(bootoverenchant*bootoverPDEF)
	}

//Shoulder Type
var shouldertype=d.gI("STY").value
var shoulderset
if (shouldertype=="0")
	{shoulderset=d.gI("SHV").value}
else if (shouldertype=="1")
	{shoulderset=d.gI("SLT").value}
else if (shouldertype=="2")
	{shoulderset=d.gI("SRB").value}

//Necklace Grade
var necklacegrade=d.gI("NCKG").value
necklacegrade=GRADE[necklacegrade]
if (necklacegrade=="S")
	{necklace=d.gI("NCKS").value}
else if (necklacegrade=="A")
	{necklace=d.gI("NCKA").value}
else if (necklacegrade=="B")
	{necklace=d.gI("NCKB").value}
else if (necklacegrade=="C")
	{necklace=d.gI("NCKC").value}
else if (necklacegrade=="D")
	{necklace=d.gI("NCKD").value}
else if (necklacegrade=="N")
	{necklace=d.gI("NCKN").value}

if (necklacegrade=="D" && LVL<20 && necklace!=0)
	{pen=pen+1;d.gI("NCK grade penalty").innerHTML=GPA}
else if (necklacegrade=="C" && LVL<40 && necklace!=0)
	{pen=pen+1;d.gI("NCK grade penalty").innerHTML=GPA}
else if (necklacegrade=="B" && LVL<52 && necklace!=0)
	{pen=pen+1;d.gI("NCK grade penalty").innerHTML=GPA}
else if (necklacegrade=="A" && LVL<61 && necklace!=0)
	{pen=pen+1;d.gI("NCK grade penalty").innerHTML=GPA}
else if (necklacegrade=="S" && LVL<76 && necklace!=0)
	{pen=pen+1;d.gI("NCK grade penalty").innerHTML=GPA}
else
	{d.gI("NCK grade penalty").innerHTML=" "}

//Necklace
Necklace=NECKLACEMDEF[necklace]
if (necklace=="6")
	{AddMP=AddMP+33}
else if (necklace=="20")
	{NECKLACEHP=445;AddMP=AddMP+42;NECKLACEPATK=1.04;NECKLACEMATK=1.08;VALAKASRD=0.05}
else if (necklace=="23")
	{AddMP=AddMP+26}
else if (necklace=="27")
	{AddMP=AddMP+42}
else if (necklace=="28")
	{AddMP=AddMP+42;FRINTEZZARD=0.05}
else if (necklace=="29" && LVL>=80)
	{AddMP=AddMP+46}

//Necklace Enchant
var necklaceenchant=d.gI("NCKE").value
if (necklaceenchant <= 3)
	{necklaceunderenchant=necklaceenchant;necklaceoverenchant=0;}
else
	{necklaceunderenchant=3;necklaceoverenchant=necklaceenchant-3;}
if (necklacegrade=="N"||necklace=="0") {
	d.gI("NCKE").disabled=true
	necklaceunderPDEF=0
	necklaceoverPDEF=0
	Necklace=Necklace
	}
else {
	d.gI("NCKE").disabled=false
	necklaceunderPDEF=1
	necklaceoverPDEF=3
	Necklace=Necklace+(necklaceunderenchant*necklaceunderPDEF)+(necklaceoverenchant*necklaceoverPDEF)
	}

//Earring1 Grade
var earring1grade=d.gI("EAR1G").value
earring1grade=GRADE[earring1grade]
if (earring1grade=="S")
	{earring1=d.gI("EAR1S").value}
else if (earring1grade=="A")
	{earring1=d.gI("EAR1A").value}
else if (earring1grade=="B")
	{earring1=d.gI("EAR1B").value}
else if (earring1grade=="C")
	{earring1=d.gI("EAR1C").value}
else if (earring1grade=="D")
	{earring1=d.gI("EAR1D").value}
else if (earring1grade=="N")
	{earring1=d.gI("EAR1N").value}

if (earring1grade=="D" && LVL<20 && earring1!=0)
	{pen=pen+1;d.gI("EAR1 grade penalty").innerHTML=GPA}
else if (earring1grade=="C" && LVL<40 && earring1!=0)
	{pen=pen+1;d.gI("EAR1 grade penalty").innerHTML=GPA}
else if (earring1grade=="B" && LVL<52 && earring1!=0)
	{pen=pen+1;d.gI("EAR1 grade penalty").innerHTML=GPA}
else if (earring1grade=="A" && LVL<61 && earring1!=0)
	{pen=pen+1;d.gI("EAR1 grade penalty").innerHTML=GPA}
else if (earring1grade=="S" && LVL<76 && earring1!=0)
	{pen=pen+1;d.gI("EAR1 grade penalty").innerHTML=GPA}
else
	{d.gI("EAR1 grade penalty").innerHTML=" "}

//Earring1
Earring1=EARRINGMDEF[earring1]
if (earring1=="5")
	{AddMP=AddMP+31;EARRINGOFANTHARASVR=0.04}
else if (earring1=="9")
	{AddMP=AddMP+31}
else if (earring1=="15")
	{AddMP=AddMP+25}
else if (earring1=="21")
	{AddMP=AddMP+20}
else if (earring1=="25")
	{AddMP=AddMP+31}
else if (earring1=="27")
	{AddMP=AddMP+31;EARRINGOFZAKENVR=0.04}
else if (earring1=="28")
	{AddMP=AddMP+35}

//Earring1 Enchant
var earring1enchant=d.gI("EAR1E").value
if (earring1enchant <= 3)
	{earring1underenchant=earring1enchant;earring1overenchant=0;}
else
	{earring1underenchant=3;earring1overenchant=earring1enchant-3;}
if (earring1grade=="N"||earring1=="0") {
	d.gI("EAR1E").disabled=true
	earring1underPDEF=0
	earring1overPDEF=0
	Earring1=Earring1
	}
else {
	d.gI("EAR1E").disabled=false
	earring1underPDEF=1
	earring1overPDEF=3
	Earring1=Earring1+(earring1underenchant*earring1underPDEF)+(earring1overenchant*earring1overPDEF)
	}

//Earring2 Grade
var earring2grade=d.gI("EAR2G").value
earring2grade=GRADE[earring2grade]
if (earring2grade=="S")
	{earring2=d.gI("EAR2S").value}
else if (earring2grade=="A")
	{earring2=d.gI("EAR2A").value}
else if (earring2grade=="B")
	{earring2=d.gI("EAR2B").value}
else if (earring2grade=="C")
	{earring2=d.gI("EAR2C").value}
else if (earring2grade=="D")
	{earring2=d.gI("EAR2D").value}
else if (earring2grade=="N")
	{earring2=d.gI("EAR2N").value}

if (earring2grade=="D" && LVL<20 && earring2!=0)
	{pen=pen+1;d.gI("EAR2 grade penalty").innerHTML=GPA}
else if (earring2grade=="C" && LVL<40 && earring2!=0)
	{pen=pen+1;d.gI("EAR2 grade penalty").innerHTML=GPA}
else if (earring2grade=="B" && LVL<52 && earring2!=0)
	{pen=pen+1;d.gI("EAR2 grade penalty").innerHTML=GPA}
else if (earring2grade=="A" && LVL<61 && earring2!=0)
	{pen=pen+1;d.gI("EAR2 grade penalty").innerHTML=GPA}
else if (earring2grade=="S" && LVL<76 && earring2!=0)
	{pen=pen+1;d.gI("EAR2 grade penalty").innerHTML=GPA}
else
	{d.gI("EAR2 grade penalty").innerHTML=" "}

//Earring2
Earring2=EARRINGMDEF[earring2]
if (earring2=="5")
	{AddMP=AddMP+31;EARRINGOFANTHARASVR=0.04}
else if (earring2=="9")
	{AddMP=AddMP+31}
else if (earring2=="15")
	{AddMP=AddMP+25}
else if (earring2=="21")
	{AddMP=AddMP+20}
else if (earring2=="25")
	{AddMP=AddMP+31}
else if (earring2=="27")
	{AddMP=AddMP+31;EARRINGOFZAKENVR=0.04}
else if (earring2=="28")
	{AddMP=AddMP+35}

//Earring2 Enchant
var earring2enchant=d.gI("EAR2E").value
if (earring2enchant <= 3)
	{earring2underenchant=earring2enchant;earring2overenchant=0;}
else
	{earring2underenchant=3;earring2overenchant=earring2enchant-3;}
if (earring2grade=="N"||earring2=="0") {
	d.gI("EAR2E").disabled=true
	earring2underPDEF=0
	earring2overPDEF=0
	Earring2=Earring2
	}
else {
	d.gI("EAR2E").disabled=false
	earring2underPDEF=1
	earring2overPDEF=3
	Earring2=Earring2+(earring2underenchant*earring2underPDEF)+(earring2overenchant*earring2overPDEF)
	}

//Ring1 Grade
var ring1grade=d.gI("RNG1G").value
ring1grade=GRADE[ring1grade]
if (ring1grade=="S")
	{ring1=d.gI("RNG1S").value}
else if (ring1grade=="A")
	{ring1=d.gI("RNG1A").value}
else if (ring1grade=="B")
	{ring1=d.gI("RNG1B").value}
else if (ring1grade=="C")
	{ring1=d.gI("RNG1C").value}
else if (ring1grade=="D")
	{ring1=d.gI("RNG1D").value}
else if (ring1grade=="N")
	{ring1=d.gI("RNG1N").value}

if (ring1grade=="D" && LVL<20 && ring1!=0)
	{pen=pen+1;d.gI("RNG1 grade penalty").innerHTML=GPA}
else if (ring1grade=="C" && LVL<40 && ring1!=0)
	{pen=pen+1;d.gI("RNG1 grade penalty").innerHTML=GPA}
else if (ring1grade=="B" && LVL<52 && ring1!=0)
	{pen=pen+1;d.gI("RNG1 grade penalty").innerHTML=GPA}
else if (ring1grade=="A" && LVL<61 && ring1!=0)
	{pen=pen+1;d.gI("RNG1 grade penalty").innerHTML=GPA}
else if (ring1grade=="S" && LVL<76 && ring1!=0)
	{pen=pen+1;d.gI("RNG1 grade penalty").innerHTML=GPA}
else
	{d.gI("RNG1 grade penalty").innerHTML=" "}

//Ring1
Ring1=RINGMDEF[ring1]
if(ring1=="8")
	{AddMP=AddMP+17}
else if(ring1=="10")
	{AddMP=AddMP+13}
else if(ring1=="13")
	{AddMP=AddMP+21;RINGOFBAIUMACC=2;RINGOFBAIUMASPD=1.04;RINGOFBAIUMCAST=1.04;RINGOFBAIUMCRITDMG=1.15}
else if(ring1=="17")
	{AddMP=AddMP+21;RINGOFCOREACC=1}
else if(ring1=="22")
	{AddMP=AddMP+21;RINGOFQUEENACC=2;RINGOFQUEENCRITDMG=1.15}
else if(ring1=="28")
	{AddMP=AddMP+21;}
else if(ring1=="29")
	{AddMP=AddMP+23;}

//Ring1 Enchant
var ring1enchant=d.gI("RNG1E").value
if (ring1enchant <= 3)
	{ring1underenchant=ring1enchant;ring1overenchant=0;}
else
	{ring1underenchant=3;ring1overenchant=ring1enchant-3;}
if (ring1grade=="N"||ring1=="0") {
	d.gI("RNG1E").disabled=true
	ring1underPDEF=0
	ring1overPDEF=0
	Ring1=Ring1
	}
else {
	d.gI("RNG1E").disabled=false
	ring1underPDEF=1
	ring1overPDEF=3
	Ring1=Ring1+(ring1underenchant*ring1underPDEF)+(ring1overenchant*ring1overPDEF)
	}

//Ring2 Grade
var ring2grade=d.gI("RNG2G").value
ring2grade=GRADE[ring2grade]
if (ring2grade=="S")
	{ring2=d.gI("RNG2S").value}
else if (ring2grade=="A")
	{ring2=d.gI("RNG2A").value}
else if (ring2grade=="B")
	{ring2=d.gI("RNG2B").value}
else if (ring2grade=="C")
	{ring2=d.gI("RNG2C").value}
else if (ring2grade=="D")
	{ring2=d.gI("RNG2D").value}
else if (ring2grade=="N")
	{ring2=d.gI("RNG2N").value}

var GPA="<font color=red><b>Grade Penalty applied</b></font>"

if (ring2grade=="D" && LVL<20 && ring2!=0)
	{pen=pen+1;d.gI("RNG2 grade penalty").innerHTML=GPA}
else if (ring2grade=="C" && LVL<40 && ring2!=0)
	{pen=pen+1;d.gI("RNG2 grade penalty").innerHTML=GPA}
else if (ring2grade=="B" && LVL<52 && ring2!=0)
	{pen=pen+1;d.gI("RNG2 grade penalty").innerHTML=GPA}
else if (ring2grade=="A" && LVL<61 && ring2!=0)
	{pen=pen+1;d.gI("RNG2 grade penalty").innerHTML=GPA}
else if (ring2grade=="S" && LVL<76 && ring2!=0)
	{pen=pen+1;d.gI("RNG2 grade penalty").innerHTML=GPA}
else
	{d.gI("RNG2 grade penalty").innerHTML=" "}

//Ring2
Ring2=RINGMDEF[ring2]
if (ring2=="8")
	{AddMP=AddMP+17}
else if(ring2=="10")
	{AddMP=AddMP+13}
else if(ring2=="13")
	{AddMP=AddMP+21;RINGOFBAIUMACC=2;RINGOFBAIUMASPD=1.04;RINGOFBAIUMCAST=1.04;RINGOFBAIUMCRITDMG=1.15}
else if(ring2=="17")
	{AddMP=AddMP+21;RINGOFCOREACC=1}
else if(ring2=="22")
	{AddMP=AddMP+21;RINGOFQUEENACC=2;RINGOFQUEENCRITDMG=1.15}
else if(ring2=="28")
	{AddMP=AddMP+21;}
else if(ring2=="29")
	{AddMP=AddMP+23;}

//Ring2 Enchant
var ring2enchant=d.gI("RNG2E").value
if (ring2enchant <= 3)
	{ring2underenchant=ring2enchant;ring2overenchant=0;}
else
	{ring2underenchant=3;ring2overenchant=ring2enchant-3;}
if (ring2grade=="N"||ring2=="0") {
	d.gI("RNG2E").disabled=true
	ring2underPDEF=0
	ring2overPDEF=0
	Ring2=Ring2
	}
else {
	d.gI("RNG2E").disabled=false
	ring2underPDEF=1
	ring2overPDEF=3
	Ring2=Ring2+(ring2underenchant*ring2underPDEF)+(ring2overenchant*ring2overPDEF)
	}

//Armor Grade Penalties
if (pen==1)
	{BuffSPEED=BuffSPEED*0.8319;BuffASPD=BuffASPD*0.8319;BuffCAST=BuffCAST*0.8319}
else if (pen==2)
	{BuffSPEED=BuffSPEED*0.694;BuffASPD=BuffASPD*0.692;BuffCAST=BuffCAST*0.695}
else if (pen==3)
	{BuffSPEED=BuffSPEED*0.58;BuffASPD=BuffASPD*0.578;BuffCAST=BuffCAST*0.58}
else if (pen>=4)
	{BuffSPEED=BuffSPEED*0.5;BuffASPD=BuffASPD*0.499;BuffCAST=BuffCAST*0.5}

//Set Bonuses
//+6 stuff that goes before Set Bonuses
if (helmetenchant>=6 && UpperSet==HelmetSet)
	{helmetench=true}
else
	{helmetench=false}
if (upperenchant>=6)
	{upperench=true}
else
	{upperench=false}
if ((lowerenchant>=6 && UpperSet==LowerSet)||Lower=="0")
	{lowerench=true}
else
	{lowerench=false}
if (gloveenchant>=6 && UpperSet==GloveSet)
	{gloveench=true}
else
	{gloveench=false}
if (bootenchant>=6 && UpperSet==BootSet)
	{bootench=true}
else
	{bootench=false}
//Apella Heavy
	if (helmet=="5" && upper=="3" && glove=="1" && boot=="3")
		{AddCP=AddCP+232}
//Apella Light
	if (helmet=="6" && upper=="1" && glove=="2" && boot=="1")
		{AddCP=AddCP+195}
//Apella Robe
	if (helmet=="4" && upper=="2" && glove=="3" && boot=="2")
		{AddCP=AddCP+177}
//Avadon Heavy
	if (helmet=="11" && upper=="6" && lower=="2" && glove=="4" && boot=="6")
		{AddHP=AddHP+294.49}
	if (helmet=="11" && upper=="6" && lower=="2" && glove=="4" && boot=="6" && shield=="2")
		{BuffShieldBlockRate=BuffShieldBlockRate*1.24}
//Avadon Light
	if (helmet=="11" && upper=="7" && glove=="4" && boot=="6")
		{BuffMDEF=BuffMDEF*1.0525;weightpenalty=weightpenalty+5795}
//Avadon Robes
	if (helmet=="11" && upper=="8" && glove=="4" && boot=="6")
		{BuffPDEF=BuffPDEF*1.0526;BuffCAST=BuffCAST*1.15}
//Blue Wolf Heavy
	if (helmet=="12" && upper=="10" && lower=="3" && glove=="5" && boot=="8")
		{STR=STR+3;CON=CON-1;DEX=DEX-2;AddSPEED=AddSPEED+7;BuffHPR=BuffHPR*1.0524}
//Blue Wolf Light
	if (helmet=="12" && upper=="11" && glove=="5" && boot=="8")
		{INT=INT-2;MEN=MEN+3;WIT=WIT-1;BuffPDEF=BuffPDEF*1.0526;BuffCAST=BuffCAST*1.15}
//Blue Wolf Robes
	if (helmet=="12" && upper=="12" && lower=="4" && glove=="5" && boot=="8")
		{INT=INT-2;MEN=MEN-1;WIT=WIT+3;AddMP=AddMP+206;BuffMPR=BuffMPR*1.0524}
//Brigandine
	if (upper=="14" && lower=="6" && helmet=="21")
		{BuffPDEF=BuffPDEF*1.05;AddHP=AddHP+153;bootench=true;gloveench=true}
	if (upper=="14" && lower=="6" && helmet=="21" && shield=="4")
		{AddHP=AddHP+20;bootench=true;gloveench=true}
//Chain Mail
	if (upper=="16" && lower=="8" && helmet=="15")
		{gloveench=true;bootench=true}
	if (upper=="16" && lower=="8" && helmet=="15" && shield=="7")
		{AddHP=AddHP+198.21;gloveench=true;bootench=true}
//Clan Oath Heavy
	if (helmet=="24" && upper=="19" && glove=="9" && boot=="17")
		{BuffPDEF=BuffPDEF*1.05;AddHP=AddHP+85}
//Clan Oath Light
	if (helmet=="25" && upper=="18" && glove=="10" && boot=="16")
		{BuffPDEF=BuffPDEF*1.05;BuffEVA=BuffEVA+3}
//Clan Oath Robes
	if (helmet=="23" && upper=="17" && glove=="11" && boot=="18")
		{BuffPDEF=BuffPDEF*1.05;BuffCAST=BuffCAST*1.05}
//Composite
	if (upper=="20" && helmet=="16")
		{gloveench=true;bootench=true;weightpenalty=weightpenalty+5795}
	if (upper=="20" && helmet=="16" && shield=="28")
		{BuffMDEF=BuffMDEF*1.0526;gloveench=true;bootench=true}
//Dark Crystal Heavy
	if (helmet=="7" && upper=="26" && lower=="13" && glove=="12" && boot=="24")
		{STR=STR-2;CON=CON+2}
	if (helmet=="7" && upper=="26" && lower=="13" && glove=="12" && boot=="24" && shield=="8")
		{BuffShieldBlockRate=BuffShieldBlockRate*1.18}
//Dark Crystal Light
	if (helmet=="7" && upper=="25" && lower=="14" && glove=="12" && boot=="24")
		{STR=STR+1;CON=CON-1;BuffASPD=BuffASPD*1.04;BuffPATK=BuffPATK*1.04}
//Dark Crystal Robe
	if (helmet=="7" && upper=="27" && glove=="12" && boot=="24")
		{WIT=WIT+2;MEN=MEN-2;BuffPDEF=BuffPDEF*1.08;BuffCAST=BuffCAST*1.15;AddSPEED=AddSPEED+7}
//Demon
	if (upper=="28" && lower=="16" && glove=="13")
		{INT=INT+4;WIT=WIT-1;AddHP=AddHP-269.65;bootench=true;helmetench=true}
//Devotion
	if (upper=="75" && lower=="44" && helmet=="31")
		{BuffCAST=BuffCAST*1.15}
//Divine
	if (upper=="29" && lower=="17" && glove=="14")
		{INT=INT-1;WIT=WIT+1;BuffPDEF=BuffPDEF*1.0526;AddMP=AddMP+170.62;bootench=true;helmetench=true}
//Doom Heavy
	if (helmet=="13" && upper=="30" && glove=="15" && boot=="27")
		{STR=STR-3;CON=CON+3;AddHP=AddHP+320}
	if (helmet=="13" && upper=="30" && glove=="15" && boot=="27" && shield=="9")
		{BuffShieldBlockRate=BuffShieldBlockRate*1.24}
//Doom Light
	if (helmet=="13" && upper=="42" && glove=="15" && boot=="27")
		{STR=STR-1;CON=CON-2;DEX=DEX+3;BuffPATK=BuffPATK*1.027;BuffMPR=BuffMPR*1.025}
//Doom Robes
	if (helmet=="13" && upper=="76" && lower=="51" && glove=="15" && boot=="27")
		{INT=INT+2;MEN=MEN+1;WIT=WIT-3;AddSPEED=AddSPEED+7;BuffMPR=BuffMPR*1.0524}
//Draconic
	if (helmet=="1" && upper=="31" && glove=="16" && boot=="28")
		{BuffASPD=BuffASPD*1.04;BuffPATK=BuffPATK*1.04;AddMP=AddMP+289;STR=STR+1;DEX=DEX+1;CON=CON-2;weightpenalty=weightpenalty+5759}
//Drake
	if (upper=="32" && boot=="29")
		{BuffMDEF=BuffMDEF*1.0524;gloveench=true;helmetench=true}
//Dynasty Heavy Shield Master
	if (helmet=="33" && upper=="84" && lower=="57" && glove=="48" && boot=="53" && LVL==80 && shoulderset==1)
		{DEX=DEX-2;STR=STR+2;BuffPDEF=BuffPDEF*1.05;AddHP=AddHP+492}
//Dynasty Heavy Bardic Master
	else if (helmet=="33" && upper=="84" && lower=="57" && glove=="48" && boot=="53" && LVL==80 && shoulderset==2)
		{DEX=DEX-2;STR=STR+2;BuffPDEF=BuffPDEF*1.05;AddHP=AddHP+492}
//Dynasty Heavy Force Master
	else if (helmet=="33" && upper=="84" && lower=="57" && glove=="48" && boot=="53" && LVL==80 && shoulderset==3)
		{DEX=DEX-1;STR=STR+2;CON=CON-1;BuffPATK=BuffPATK*1.05;AddHP=AddHP+492}
//Dynasty Heavy Weapon Master
	else if (helmet=="33" && upper=="84" && lower=="57" && glove=="48" && boot=="53" && LVL==80 && shoulderset==4)
		{DEX=DEX-1;STR=STR+2;CON=CON-1;BuffPATK=BuffPATK*1.05;AddHP=AddHP+492}
//Dynasty Heavy no shoulder
	else if (helmet=="33" && upper=="84" && lower=="57" && glove=="48" && boot=="53")
		{DEX=DEX-1;STR=STR+1;BuffPDEF=BuffPDEF*1.05;AddHP=AddHP+393}
//Dynasty Light Bow Master
	if (helmet=="33" && upper=="85" && lower=="58" && glove=="48" && boot=="53" && LVL==80 && shoulderset==5)
		{CON=CON-2;DEX=DEX+1;STR=STR+1;BuffEVA=BuffEVA+4.3;AddHP=AddHP+492}
//Dynasty Light Dagger Master
	else if (helmet=="33" && upper=="85" && lower=="58" && glove=="48" && boot=="53" && LVL==80 && shoulderset==6)
		{CON=CON-2;DEX=DEX+1;STR=STR+1;BuffACC=BuffACC+4.3;AddHP=AddHP+492}
//Dynasty Light
	else if (helmet=="33" && upper=="85" && lower=="58" && glove=="48" && boot=="53")
		{CON=CON-1;DEX=DEX+1;BuffACC=BuffACC+1.5;BuffEVA=BuffEVA+1.5;AddHP=AddHP+393}
//Dynasty Robe Buff Master
	if (helmet=="33" && upper=="86" && lower=="59" && glove=="48" && boot=="53" && LVL==80 && shoulderset==7)
		{INT=INT-1;WIT=WIT-1;MEN=MEN+2;BuffCAST=BuffCAST*1.07;AddMP=AddMP+321}
//Dynasty Robe Heal Master
	else if (helmet=="33" && upper=="86" && lower=="59" && glove=="48" && boot=="53" && LVL==80 && shoulderset==8)
		{INT=INT-1;WIT=WIT+2;MEN=MEN-1;BuffCAST=BuffCAST*1.07;AddMP=AddMP+321}
//Dynasty Robe Summon Master
	else if (helmet=="33" && upper=="86" && lower=="59" && glove=="48" && boot=="53" && LVL==80 && shoulderset==9)
		{INT=INT-1;WIT=WIT-1;MEN=MEN+2;BuffCAST=BuffCAST*1.07;AddMP=AddMP+321}
//Dynasty Robe Wizard Master
	else if (helmet=="33" && upper=="86" && lower=="59" && glove=="48" && boot=="53" && LVL==80 && shoulderset==10)
		{INT=INT-1;WIT=WIT+2;MEN=MEN-1;BuffCAST=BuffCAST*1.07;AddMP=AddMP+321}
//Dynasty Robe
	else if (helmet=="33" && upper=="86" && lower=="59" && glove=="48" && boot=="53")
		{INT=INT-1;MEN=MEN+1;BuffCAST=BuffCAST*1.05;AddHP=AddHP+257}
//Elven Mithril
	if (upper=="54" && lower=="34" && glove=="19")
		{AddSPEED=AddSPEED+7;WIT=WIT+1;INT=INT-1;bootench=true;helmetench=true}
//Full Plate
	if (upper=="37" && helmet=="17")
		{AddHP=AddHP+269.65;gloveench=true;bootench=true}
	if (upper=="37" && helmet=="17" && shield=="12")
		{BuffShieldBlockRate=BuffShieldBlockRate*1.0526}
//Imperial Crusader
	if (helmet=="2" && upper=="40" && lower=="24" && glove=="27" && boot=="33")
		{AddHP=AddHP+445;BuffPDEF=BuffPDEF*1.08;DEX=DEX-2;STR=STR+2}
//Karmian
	if (upper=="41" && lower=="26" && glove=="28")
		{BuffCAST=BuffCAST*1.15;BuffPDEF=BuffPDEF*1.0526;bootench=true;helmetench=true}
//Knowledge
	if (upper=="77" && lower=="52" && glove=="24")
		{BuffMATK=BuffMATK*1.1025;BuffMPR=BuffMPR*0.95;bootench=true;helmetench=true}
//Majestic Heavy
	if (helmet=="9" && upper=="47" && glove=="31" && boot=="40")
		{STR=STR+2;CON=CON-2;BuffPATK=BuffPATK*1.04;BuffACC=BuffACC+3}
//Majestic Light
	if (helmet=="9" && upper=="46" && glove=="31" && boot=="40" && WpnMAS!="Bow")
		{DEX=DEX+1;CON=CON-1;AddMP=AddMP+240;weightpenalty=weightpenalty+5759}
	else if (helmet=="9" && upper=="46" && glove=="31" && boot=="40" && WpnMAS=="Bow")
		{DEX=DEX+1;CON=CON-1;AddMP=AddMP+240;BuffPATK=BuffPATK*1.08;weightpenalty=weightpenalty+5759}
//Majestic Robe
	if (helmet=="9" && upper=="48" && glove=="31" && boot=="40")
		{MEN=MEN+1;INT=INT-1;AddMP=AddMP+240;BuffCAST=BuffCAST*1.15;BuffMPR=BuffMPR*1.08}
//Major Arcana
	if (helmet=="3" && upper=="49" && boot=="41" && glove=="32")
		{BuffMATK=BuffMATK*1.17;AddSPEED=AddSPEED+7;WIT=WIT+1;INT=INT+1;MEN=MEN-2;weightpenalty=weightpenalty+5759}
//Manticore
	if (upper=="50" && lower=="30" && boot=="42")
		{AddMP=AddMP+91.81;gloveench=true;helmetench=true}
//Mithril Heavy
	if (upper=="52" && lower=="32" && helmet=="26")
		{bootench=true;gloveench=true}
	if (upper=="52" && lower=="32" && helmet=="26" && shield=="13")
		{AddHP=AddHP+126.13;bootench=true;gloveench=true}
//Mithril Light
	if (upper=="53" && lower=="47" && boot=="43")
		{BuffEVA=BuffEVA+4;gloveench=true;helmetench=true}
//Nightmare Heavy
	if (helmet=="8" && upper=="5" && glove=="25" && boot=="11")
		{CON=CON+2;DEX=DEX-2;BuffPATK=BuffPATK*1.04}
	if (helmet=="8" && upper=="5" && glove=="25" && boot=="11" && shield=="22")
		{BuffRD=BuffRD+0.05}
//Nightmare Light
	if (helmet=="8" && upper=="56" && glove=="25" && boot=="11")
		{DEX=DEX+1;CON=CON-1;BuffMDEF=BuffMDEF*1.04;BuffVR=BuffVR+0.03}
//Nightmare Robe
	if (helmet=="8" && upper=="63" && glove=="25" && boot=="11")
		{INT=INT+2;WIT=WIT-2;BuffMATK=BuffMATK*1.08;BuffMPR=BuffMPR*1.04}
//Plated Leather
	if (upper=="58" && lower=="39" && boot=="45")
		{STR=STR+4;CON=CON-1;gloveench=true;helmetench=true}
//Reinforced Leather
	if (upper=="60" && lower=="41" && boot=="46")
		{AddMP=AddMP+80;gloveench=true;helmetench=true}
//Tallum Heavy
	if (helmet=="10" && upper=="71" && glove=="45" && boot=="50")
		{STR=STR+2;CON=CON-2;BuffASPD=BuffASPD*1.08;weightpenalty=weightpenalty+5759}
//Tallum Light
	if (helmet=="10" && upper=="70" && glove=="45" && boot=="50")
		{MEN=MEN+2;WIT=WIT-2;AddMP=AddMP+222;BuffMPR=BuffMPR*1.08}
//Tallum Robe
	if (helmet=="10" && upper=="72" && lower=="46" && glove=="45" && boot=="50")
		{INT=INT-2;WIT=WIT+2;BuffCAST=BuffCAST*1.15;BuffMDEF=BuffMDEF*1.08}
//Theca
	if (upper=="73" && lower=="48" && boot=="51")
		{BuffPDEF=BuffPDEF*1.0526;gloveench=true;helmetench=true}
//Wooden
	if (upper=="81" && lower=="54" && helmet=="32")
		{BuffPDEF=BuffPDEF*1.02;AddHP=AddHP+41}
//Zubei Heavy
	if (helmet=="14" && upper=="82" && lower=="55" && glove=="47" && boot=="52")
		{BuffPDEF=BuffPDEF*1.0526;AddHP=AddHP+294.49}
//Zubei Light
	if (helmet=="14" && upper=="83" && lower=="56" && glove=="47" && boot=="52")
		{BuffEVA=BuffEVA+4}
//Zubei Robes
	if (helmet=="14" && upper=="79" && lower=="53" && glove=="47" && boot=="52")
		{BuffMATK=BuffMATK*1.1025;BuffMPR=BuffMPR*0.95}
//+6 Heavy Armor Bonuses
	if (uppergrade=="S" && AType=="H" && helmetench==true && upperench==true && lowerench==true && gloveench==true && bootench==true && UpperSet!=0)
		{AddPDEF=AddPDEF+56;AddMPR=AddMPR+(2*movemode)}
	if (uppergrade=="A" && AType=="H" && helmetench==true && upperench==true && lowerench==true && gloveench==true && bootench==true && UpperSet!=0)
		{AddPDEF=AddPDEF+50;AddMPR=AddMPR+(2*movemode)}
	if (uppergrade=="B" && AType=="H" && helmetench==true && upperench==true && lowerench==true && gloveench==true && bootench==true && UpperSet!=0)
		{AddPDEF=AddPDEF+44;AddMPR=AddMPR+(2*movemode)}
	if (uppergrade=="C" && AType=="H" && helmetench==true && upperench==true && lowerench==true && gloveench==true && bootench==true && UpperSet!=0)
		{AddPDEF=AddPDEF+38;AddMPR=AddMPR+(2*movemode)}
	if (uppergrade=="D" && AType=="H" && helmetench==true && upperench==true && lowerench==true && gloveench==true && bootench==true && UpperSet!=0)
		{AddPDEF=AddPDEF+25;AddMPR=AddMPR+(2*movemode)}
//+6 Light Armor Bonus
	if (uppergrade=="S" && AType=="L" && helmetench==true && upperench==true && lowerench==true && gloveench==true && bootench==true && UpperSet!=0)
		{AddMDEF=AddMDEF+32;BuffEVA=BuffEVA+2}
	if (uppergrade=="A" && AType=="L" && helmetench==true && upperench==true && lowerench==true && gloveench==true && bootench==true && UpperSet!=0)
		{AddMDEF=AddMDEF+28;BuffEVA=BuffEVA+2}
	if (uppergrade=="B" && AType=="L" && helmetench==true && upperench==true && lowerench==true && gloveench==true && bootench==true && UpperSet!=0)
		{AddMDEF=AddMDEF+24;BuffEVA=BuffEVA+2}
	if (uppergrade=="C" && AType=="L" && helmetench==true && upperench==true && lowerench==true && gloveench==true && bootench==true && UpperSet!=0)
		{AddMDEF=AddMDEF+20;BuffEVA=BuffEVA+2}
	if (uppergrade=="D" && AType=="L" && helmetench==true && upperench==true && lowerench==true && gloveench==true && bootench==true && UpperSet!=0)
		{AddMDEF=AddMDEF+12;BuffEVA=BuffEVA+2}
//+6 Robe Bonus
	if (uppergrade=="S" && AType=="R" && helmetench==true && upperench==true && lowerench==true && gloveench==true && bootench==true && UpperSet!=0)
		{weightlimit=weightlimit*1.3;AddPDEF=AddPDEF+36}
	if (uppergrade=="A" && AType=="R" && helmetench==true && upperench==true && lowerench==true && gloveench==true && bootench==true && UpperSet!=0)
		{weightlimit=weightlimit*1.3;AddPDEF=AddPDEF+32}
	if (uppergrade=="B" && AType=="R" && helmetench==true && upperench==true && lowerench==true && gloveench==true && bootench==true && UpperSet!=0)
		{weightlimit=weightlimit*1.3;AddPDEF=AddPDEF+28}
	if (uppergrade=="C" && AType=="R" && helmetench==true && upperench==true && lowerench==true && gloveench==true && bootench==true && UpperSet!=0)
		{weightlimit=weightlimit*1.3;AddPDEF=AddPDEF+24}
	if (uppergrade=="D" && AType=="R" && helmetench==true && upperench==true && lowerench==true && gloveench==true && bootench==true && UpperSet!=0)
		{weightlimit=weightlimit*1.3;AddPDEF=AddPDEF+16}

//Augmentations
var augment1 = + d.gI("WAU1").value
var augment12 = + d.gI("AUG1").value
	if (augment1==2)
		{BuffACC=BuffACC+augment12}
	if (augment1==3)
		{CON=CON+augment12}
	if (augment1==4)
		{AddCP=AddCP+augment12}
	if (augment1==5)
		{AddCPR=AddCPR+augment12}
	if (augment1==6)
		{AddCRIT=AddCRIT+augment12}
	if (augment1==7)
		{BuffEVA=BuffEVA+augment12}
	if (augment1==8)
		{AddHP=AddHP+augment12}
	if (augment1==9)
		{AddHPR=AddHPR+augment12}
	if (augment1==10)
		{INT=INT+augment12}
	if (augment1==11)
		{AddMATK=AddMATK+augment12}
	if (augment1==12)
		{AddMDEF=AddMDEF+augment12}
	if (augment1==13)
		{MEN=MEN+augment12}
	if (augment1==14)
		{AddMP=AddMP+augment12}
	if (augment1==15)
		{AddMPR=AddMPR+augment12}
	if (augment1==16)
		{AddPATK=AddPATK+augment12}
	if (augment1==17)
		{AddPDEF=AddPDEF+augment12}
	if (augment1==18)
		{STR=STR+augment12}
var augment2 = + d.gI("WAU2").value
var augment22 = + d.gI("AUG2").value
	if (augment2==2)
		{BuffACC=BuffACC+augment22}
	if (augment2==3)
		{CON=CON+augment22}
	if (augment2==4)
		{AddCP=AddCP+augment22}
	if (augment2==5)
		{AddCPR=AddCPR+augment22}
	if (augment2==6)
		{AddCRIT=AddCRIT+augment22}
	if (augment2==7)
		{BuffEVA=BuffEVA+augment22}
	if (augment2==8)
		{AddHP=AddHP+augment22}
	if (augment2==9)
		{AddHPR=AddHPR+augment22}
	if (augment2==10)
		{INT=INT+augment22}
	if (augment2==11)
		{AddMATK=AddMATK+augment22}
	if (augment2==12)
		{AddMDEF=AddMDEF+augment22}
	if (augment2==13)
		{MEN=MEN+augment22}
	if (augment2==14)
		{AddMP=AddMP+augment22}
	if (augment2==15)
		{AddMPR=AddMPR+augment22}
	if (augment2==16)
		{AddPATK=AddPATK+augment22}
	if (augment2==17)
		{AddPDEF=AddPDEF+augment22}
	if (augment2==18)
		{STR=STR+augment22}
var augment3 = + d.gI("WAU3").value
var augment32 = + d.gI("AUG3").value
	if (augment3==2)
		{BuffACC=BuffACC+augment32}
	if (augment3==3)
		{CON=CON+augment32}
	if (augment3==4)
		{AddCP=AddCP+augment32}
	if (augment3==5)
		{AddCPR=AddCPR+augment32}
	if (augment3==6)
		{AddCRIT=AddCRIT+augment32}
	if (augment3==7)
		{BuffEVA=BuffEVA+augment32}
	if (augment3==8)
		{AddHP=AddHP+augment32}
	if (augment3==9)
		{AddHPR=AddHPR+augment32}
	if (augment3==10)
		{INT=INT+augment32}
	if (augment3==11)
		{AddMATK=AddMATK+augment32}
	if (augment3==12)
		{AddMDEF=AddMDEF+augment32}
	if (augment3==13)
		{MEN=MEN+augment32}
	if (augment3==14)
		{AddMP=AddMP+augment32}
	if (augment3==15)
		{AddMPR=AddMPR+augment32}
	if (augment3==16)
		{AddPATK=AddPATK+augment32}
	if (augment3==17)
		{AddPDEF=AddPDEF+augment32}
	if (augment3==18)
		{STR=STR+augment32}
var augment4 = + d.gI("WAU4").value
var augment42 = + d.gI("AUG4").value
	if (augment4==2)
		{BuffACC=BuffACC+augment42}
	if (augment4==3)
		{CON=CON+augment42}
	if (augment4==4)
		{AddCP=AddCP+augment42}
	if (augment4==5)
		{AddCPR=AddCPR+augment42}
	if (augment4==6)
		{AddCRIT=AddCRIT+augment42}
	if (augment4==7)
		{BuffEVA=BuffEVA+augment42}
	if (augment4==8)
		{AddHP=AddHP+augment42}
	if (augment4==9)
		{AddHPR=AddHPR+augment42}
	if (augment4==10)
		{INT=INT+augment42}
	if (augment4==11)
		{AddMATK=AddMATK+augment42}
	if (augment4==12)
		{AddMDEF=AddMDEF+augment42}
	if (augment4==13)
		{MEN=MEN+augment42}
	if (augment4==14)
		{AddMP=AddMP+augment42}
	if (augment4==15)
		{AddMPR=AddMPR+augment42}
	if (augment4==16)
		{AddPATK=AddPATK+augment42}
	if (augment4==17)
		{AddPDEF=AddPDEF+augment42}
	if (augment4==18)
		{STR=STR+augment42}
var augment5 = + d.gI("WAU5").value
var augment52 = + d.gI("AUG5").value
	if (augment5==2)
		{BuffACC=BuffACC+augment52}
	if (augment5==3)
		{CON=CON+augment52}
	if (augment5==4)
		{AddCP=AddCP+augment52}
	if (augment5==5)
		{AddCPR=AddCPR+augment52}
	if (augment5==6)
		{AddCRIT=AddCRIT+augment52}
	if (augment5==7)
		{BuffEVA=BuffEVA+augment52}
	if (augment5==8)
		{AddHP=AddHP+augment52}
	if (augment5==9)
		{AddHPR=AddHPR+augment52}
	if (augment5==10)
		{INT=INT+augment52}
	if (augment5==11)
		{AddMATK=AddMATK+augment52}
	if (augment5==12)
		{AddMDEF=AddMDEF+augment52}
	if (augment5==13)
		{MEN=MEN+augment52}
	if (augment5==14)
		{AddMP=AddMP+augment52}
	if (augment5==15)
		{AddMPR=AddMPR+augment52}
	if (augment5==16)
		{AddPATK=AddPATK+augment52}
	if (augment5==17)
		{AddPDEF=AddPDEF+augment52}
	if (augment5==18)
		{STR=STR+augment52}

//////////////////Enemy Targets
var TARGET=new Array(
new Array("Generic Player",LVL,"player",30,1.1,1.2,1.1,0.9,"Sword",0,80,379,"S","bu",244,1.085,1,0,300,1,0,0,0,0,0,1,0,1,0,0,1,0,false,0,0,0),
new Array("Generic Monster",LVL,"monster",30,1.1,1.2,1.1,0.9,"Sword",0,80,379,"N","no SA",244,1,1,0,300,1,0,0,0,0,0,1,0,1,0,0,1,0,false,0,0,0),
new Array("lv40 Temple Knight",40,"player",35,1.15,1.04,1.1,0.9,"Sword",0,80,379,"C","bu",165,1.085,1,19.1,394,1,73.5,0,-8,73.3,0,1,0,1,0,206,1,0,false,1.94731,0,0),
new Array("lv40 Hawkeye",40,"player",34,1.14,1.65,1.05,0.95,"Bow",-3.75,120,293,"C","bu",341,1.085,1,182.8,301,1,31.8,0,12,0,0.2,1.05,0,1,56,0,1,0,false,0,0,1500),
new Array("lv40 Prophet",40,"player",21,1.01,0.63,1.2,0.8,"Blunt",4.75,40,379,"C","bu",165,1.45,1,18.1,245,1.0526,38,0,-8,0,0,1,0,1,0,206,1,0,false,1,0,0),
new Array("lv61 Temple Knight",61,"player",33,1.13,1.04,1.1,0.9,"Sword",0,80,379,"A","bu",244,1.085,1.04,50.4,483,1,346.4,0,-8,0,0,1,0,1,0,259,1,0,true,3,0.05,0),
new Array("lv61 Hawkeye",61,"player",35,1.15,1.43,1.05,0.95,"YumiBow",-3.75,120,227,"A","bu",552,1.085,1.08,497.1,410,1,57.5,0,12,0,0.2,1.05,0,1,56,0,1,0,false,0,0,696.915),
new Array("lv61 Prophet",61,"player",21,1.01,0.63,1.2,0.8,"Blunt",4.75,40,379,"A","bu",244,1.45,1,49.4,319,1.08,69.5,0,-8,0,0,1,0,1,0,259,1,0,false,1,0,0),
new Array("lv80 Temple Knight",80,"player",33,1.13,1.12,1.1,0.9,"Sword",0,80,379,"S","bu",296,1.085,1.04,80.4,545,1.08,556.3,4,-8,84.3,0,1.04,0,1.3225,0,293,1.1,599,true,3,0.05,0),
new Array("lv80 Hawkeye",80,"player",35,1.15,1.48,1.05,0.95,"Bow",-3.75,120,293,"S","bu",611,1.085,1.0816,998.6,461,1,79.6,4,12,88.1,0.2,1.13568,0,1.3225,56,0,1,0,false,0,0.05,1500),
new Array("lv80 Prophet",80,"player",21,1.01,0.63,1.2,0.8,"Blunt",4.75,40,379,"S","bu",296,1.45,1.04,79.4,319,1.08,96.6,4,-8,0,0,1.04,0,1.3225,0,293,1,0,false,1,0.05,0),
new Array("clone"),
0)

//Enemy Stats
var enemy = + d.gI("ENE").value
if (enemy!=11) {
	d.gI("LVO").disabled=false
//Enemy Base Stats
	var ELVL=TARGET[enemy][1]
	var ELVLMOD=(ELVL+89)/100
	var targettype=TARGET[enemy][2]
	var EDEX=TARGET[enemy][3]
	var EDEXMOD=TARGET[enemy][4]
	var ESTRMOD=TARGET[enemy][5]
//Enemy Weapon
	var Eranddmg=TARGET[enemy][6]
	var Eminranddmg=TARGET[enemy][7]
	var EWpnType=TARGET[enemy][8]
	var EWpnAcc=TARGET[enemy][9]
	var EWpnCrt=TARGET[enemy][10]
	var EWpnSpd=TARGET[enemy][11]
	var Eweapongrade=TARGET[enemy][12]
	var Eweapon_sa=TARGET[enemy][13]
	var Ebowdelay=TARGET[enemy][35]
//Enemy P.Atk.
	var EWpnPATK=TARGET[enemy][14]
	var EMasteryPATK=TARGET[enemy][15]
	var EBuffPATK=TARGET[enemy][16]
	var EAddPATK=TARGET[enemy][17]
//Enemy P.Def.
	var Eapdef=TARGET[enemy][18]
	var EBuffPDEF=TARGET[enemy][19]
	var EAddPDEF=TARGET[enemy][20]
//Enemy Accuracy
	var EBuffACC=TARGET[enemy][21]
//Enemy Evasion
	var EBuffEVA=TARGET[enemy][22]
//Enemy Critical
	var Ebasecritical=EWpnCrt*EDEXMOD
	var EAddCRIT=TARGET[enemy][23]
	var Esubcritical=Ebasecritical*(TARGET[enemy][24])
//Enemy Atk. Spd.
	var EBuffASPD=TARGET[enemy][25]
	var EAddASPD=TARGET[enemy][26]
//Enemy Critical Damage
	var EBuffCRITDMG=TARGET[enemy][27]
	var EAddCRITDMG=TARGET[enemy][28]
//Enemy Reflect Damage
	var Ebuffrd=TARGET[enemy][34]
	d.gI("EBUFFS").disabled=false
//Enemy Buffs
	var EBuffShieldBlockRate=1
	var EBuffSHIELDPDEF=TARGET[enemy][30]
	var ebuffs=d.gI("EBUFFS").value
	if (ebuffs=="1" && enemy==2)
		{EBuffPATK=EBuffPATK*1.12;EBuffPDEF=EBuffPDEF*1.12;}
	if (ebuffs=="1" && (enemy==5||enemy==8))
		{EBuffPATK=EBuffPATK*1.12;EBuffPDEF=EBuffPDEF*1.12;}
	if (enemy==3)
		{EBuffASPD=EBuffASPD*1.08}
	if (enemy==6||enemy==9)
		{EBuffASPD=EBuffASPD*1.12}
	if (ebuffs=="1" && enemy==4)
		{EBuffPATK=EBuffPATK*1.15*1.05;EBuffPDEF=EBuffPDEF*1.0925;EBuffACC=EBuffACC+2;EBuffEVA=EBuffEVA-2;Esubcritical=Esubcritical+Ebasecritical*0.2;EBuffASPD=EBuffASPD*1.05;EBuffShieldBlockRate=EBuffShieldBlockRate*1.3;EBuffCRITDMG=EBuffCRITDMG*1.3}
	if (ebuffs=="1" && (enemy==7||enemy==10)||(ebuffs>=2 && ebuffs<=6 && enemy!=2 && enemy!=5 && enemy!=8))
		{EBuffPATK=EBuffPATK*1.242;EBuffPDEF=EBuffPDEF*1.058;EBuffACC=EBuffACC+4;EBuffEVA=EBuffEVA-4;Esubcritical=Esubcritical+Ebasecritical*0.3;EBuffASPD=EBuffASPD*1.4364;EBuffShieldBlockRate=EBuffShieldBlockRate*1.5;EBuffCRITDMG=EBuffCRITDMG*1.35}
	if ((ebuffs>=2 && ebuffs<=6 && (enemy==2||enemy==5||enemy==8)))
		{EBuffPATK=EBuffPATK*1.15;EBuffPDEF=EBuffPDEF*1.15;EBuffACC=EBuffACC+4;Esubcritical=Esubcritical+Ebasecritical*0.3;EBuffASPD=EBuffASPD*1.33;EBuffShieldBlockRate=EBuffShieldBlockRate*1.5;EBuffCRITDMG=EBuffCRITDMG*1.35}
	if (ebuffs=="3"||ebuffs=="5"||ebuffs=="6")
		{EBuffPATK=EBuffPATK*1.12;EBuffASPD=EBuffASPD*1.15;EBuffCRITDMG=EBuffCRITDMG*1.35}
	if (ebuffs=="4"||ebuffs=="5"||ebuffs=="6")
		{EBuffPDEF=EBuffPDEF*1.25;Esubcritical=Esubcritical+Ebasecritical}
	if (ebuffs=="6")
		{EBuffPATK=EBuffPATK*1.1*1.1;EBuffPDEF=EBuffPDEF*1.2;EBuffACC=EBuffACC+8;EBuffEVA=EBuffEVA+7;Esubcritical=Esubcritical+Ebasecritical*0.5;EBuffASPD=EBuffASPD*1.2;EBuffShieldBlockRate=EBuffShieldBlockRate/1.5*1.8;EBuffCRITDMG=EBuffCRITDMG*1.25*1.2;EBuffSHIELDPDEF=EBuffSHIELDPDEF*2;Ebuffrd=Ebuffrd+0.4}
//Enemy Debuffs
	if (d.gI("EDEPC").checked==true)
		{var depatk=d.gI("EDEP").value;EBuffPATK=EBuffPATK*WEAKNESS[depatk]}
	if (d.gI("EDEDC").checked==true)
		{var depdef=d.gI("EDED").value;EBuffPDEF=EBuffPDEF*HEX[depdef]}
	if (d.gI("EBLKSC").checked==true && ((ebuffs=="1" && enemy==2)||(ebuffs=="1" && (enemy==5||enemy==8))))
		{EBuffPDEF=(EBuffPDEF/1.12)*0.9}
	else if (d.gI("EBLKSC").checked==true && ((ebuffs=="1" && enemy==4)||(ebuffs=="1" && (enemy==7||enemy==10)||(ebuffs>=2 && ebuffs<=6 && enemy!=2 && enemy!=5 && enemy!=8))))
		{EBuffPDEF=0.90*EBuffPDEF}
	else if (d.gI("EBLKSC").checked==true)
		{EBuffPDEF=0.90*EBuffPDEF}
	if (d.gI("EDEAC").checked==true)
		{var bu=d.gI("EDEA").value;EBuffACC=EBuffACC-DEACC[bu]}
	if (d.gI("ESBBC").checked==true)
		{var sandbomb=d.gI("ESBB").value;EBuffACC=EBuffACC+SANDBOMB[sandbomb]}
	if (d.gI("ETRBC").checked==true)
		{var tribunal=d.gI("ETRB").value;Esubcritical=Esubcritical+(Ebasecritical*TRIBUNAL[tribunal])}
	if (d.gI("EDENC").checked==true)
		{var bu=d.gI("EDEN").value;EBuffASPD=EBuffASPD*DEASPD[bu]}
	if (d.gI("EJDGC").checked==true)
		{var iv=d.gI("EJDG").value;EBuffCRITDMG=EBuffCRITDMG*JUDGMENT[iv]}
	if (d.gI("EARCRC").checked==true)
		{EBuffPDEF=0.70*EBuffPDEF}
	if (d.gI("ECBYC").checked==true)
		{EBuffEVA=EBuffEVA-6}
	if (d.gI("ECDMC").checked==true)
		{EBuffPATK=0.83*EBuffPATK}
	if (d.gI("ECSHC").checked==true)
		{var bu=d.gI("ECSH").value;EBuffPDEF=CURSEOFSHADE[bu]*EBuffPDEF}
	if (d.gI("EDBDC").checked==true)
		{var bu=d.gI("EDBD").value;EBuffPATK=EBuffPATK*DEMONICBD[bu]}
	if (d.gI("EFVC").checked==true)
		{EBuffASPD=EBuffASPD*0.7}
	if (d.gI("EHEEC").checked==true) 
		{var bu=d.gI("HEE").value;EBuffPDEF=EBuffPDEF*0.5;EBuffEVA=EBuffEVA-16;EBuffShieldBlockRate=EBuffShieldBlockRate*0.5}
	if (d.gI("ECHOLC").checked==true)
		{var bu=d.gI("ECHOL").value;EBuffACC=EBuffACC+HSCHOLACC[bu];EBuffEVA=EBuffEVA+HSCHOLEVA[bu]}
	if (d.gI("EIVC").checked==true)
		{var iv=d.gI("EIV").value;EBuffASPD=EBuffASPD*0.9}
	if (d.gI("ELVORC").checked==true)
		{var bu=d.gI("ELVOR").value;EBuffACC=EBuffACC-6}
	if (d.gI("EPSYC").checked==true)
		{var psycho=d.gI("EPSY").value;EBuffASPD=EBuffASPD*PSYCHO[psycho]}
	if (d.gI("ESBC").checked==true)
		{var shockblast=d.gI("ESB").value;EBuffPDEF=0.70*EBuffPDEF}
	if (d.gI("EDERC").checked==true)
		{var despair=d.gI("EDER").value;EBuffACC=EBuffACC-6;Esubcritical=Esubcritical+(Ebasecritical*-0.3);EBuffASPD=EBuffASPD*0.8;EBuffCRITDMG=EBuffCRITDMG*0.7}
	if (d.gI("EWVC").checked==true)
		{var wv=d.gI("EWV").value;EBuffASPD=EBuffASPD*0.9}
//Enemy final stats
	var Ecombatpatk=EWpnPATK*ESTRMOD*ELVLMOD*EMasteryPATK*EBuffPATK+EAddPATK
	if (((Eweapongrade=="S"||Eweapongrade=="A"||Eweapongrade=="S80") && Eweapon_sa!="no SA")||Eweapongrade=="H")
		{Ecombatpatk=Ecombatpatk*1.05}
	if (targettype=="player")
		{Ecombatpatk=Ecombatpatk*2}
	var Ecombatpdef=Eapdef*ELVLMOD*EBuffPDEF+EAddPDEF
	var EAccuracy=(Math.sqrt(EDEX)*6)+ELVL+EWpnAcc+EBuffACC
	var EEvasion=(Math.sqrt(EDEX)*6)+ELVL+EBuffEVA
	var Ecombatcritical=Ebasecritical+EAddCRIT+Esubcritical
	if (Ecombatcritical>500)
		{Ecombatcritical=500}
	var Ecombatatkspd=EWpnSpd*EDEXMOD*EBuffASPD+EAddASPD
//Enemy Shield stats
	var EShield=TARGET[enemy][29]
	var EAddSHIELDPDEF=TARGET[enemy][31]
	var Eshieldpdef=EShield*EBuffSHIELDPDEF+Ecombatpdef+EAddSHIELDPDEF
	var Eblock360=TARGET[enemy][32]
	var EBuffShieldBlockRate=EBuffShieldBlockRate*TARGET[enemy][33]
	var EShieldBlockRate=EBaseShieldBlockRate*EBuffShieldBlockRate*EDEXMOD
	if ((position==2||position==3) && block360==false)
		{EShieldBlockRate=0}
	if (EShieldBlockRate>=1)
		{EShieldBlockRate=1}
	var Eavgshieldpdef=Eshieldpdef*EShieldBlockRate+Ecombatpdef*(1-EShieldBlockRate)}
if (enemy==11)
	{var EWpnType=WpnType}

//Stat Modifiers
STRMOD=STRMODIFIER[STR]
DEXMOD=DEXMODIFIER[DEX]
CONMOD=CONMODIFIER[CON]
INTMOD=INTMODIFIER[INT]
WITMOD=WITMODIFIER[WIT]
MENMOD=MENMODIFIER[MEN]

//Calculations that must take place before buffs
var basecritical=WpnCrt*DEXMOD

//Buffs
//Accuracy
	if (d.gI("ACCTC").checked==true)
		{BuffACC=BuffACC+3}
//Accuracy Debuffs
	if (d.gI("DEAC").checked==true)
		{var bu=d.gI("DEA").value;BuffACC=BuffACC-DEACC[bu]}
//Acumen
	if (d.gI("ACUC").checked==true)
		{var bu=d.gI("ACU").value;BuffCAST=BuffCAST*ACUMEN[bu]}
//Advanced Block
	if (d.gI("ADBC").checked==true)
		{var bu=d.gI("ADB").value;BuffSHIELDPDEF=BuffSHIELDPDEF*ADVANCEDBLOCK[bu]}
//Aegis Stance
	if (d.gI("ASTC").checked==true)
		{BuffSHIELDPDEF=BuffSHIELDPDEF*0.6;block360=true}
//Agility
	if (d.gI("AGIC").checked==true)
		{var bu=d.gI("AGI").value;BuffEVA=BuffEVA+AGILITY[bu]}
//Angelic Icon
	if (d.gI("ANIC").checked==true)
		{var bu=d.gI("ANI").value
		AddSPEED=AddSPEED+ANGELICSPEED[bu]}
	if (d.gI("ANIC").checked==true && (WpnType=="Fist"||WpnType=="2hb"||WpnType=="Blunt"))
		{var bu=d.gI("ANI").value
		BuffACC=BuffACC+6
		BuffPDEF=BuffPDEF*1.5
		BuffMDEF=BuffMDEF*1.5
		BuffASPD=BuffASPD*ANGELICASPD[bu]
		BuffCRITDMG=BuffCRITDMG*ANGELICCRITDMG[bu]}
	if (d.gI("ANIC").checked==true && (WpnType=="Polearm"||WpnType=="2hs"||WpnType=="Sword"))
		{var bu=d.gI("ANI").value
		AddCRIT=AddCRIT+ANGELICCRIT[bu]
		BuffACC=BuffACC+6
		BuffPDEF=BuffPDEF*1.5
		BuffMDEF=BuffMDEF*1.5
		BuffASPD=BuffASPD*ANGELICASPD[bu]}
//Arcane Agility
	if (d.gI("ARAC").checked==true)
		{BuffCAST=BuffCAST*1.2}
//Arcane Chaos
	if (d.gI("ARNC").checked==true)
		{AddMPR=AddMPR-24}
//Arcane Power
	if (d.gI("ARPC").checked==true)
		{BuffMATK=1.3*BuffMATK}
//Arcane Wisdom
	if (d.gI("ARWC").checked==true)
		{BuffCAST=BuffCAST*0.9}
//Armor Crush
	if (d.gI("ACC").checked==true)
		{BuffPDEF=0.70*BuffPDEF;BuffMDEF=0.70*BuffMDEF}
//Attack Speed Debuffs
	if (d.gI("DENC").checked==true)
		{var bu=d.gI("DEN").value;BuffASPD=BuffASPD*DEASPD[bu]}
//Berserker Spirit
	if (d.gI("ZERKC").checked==true)
		{var bu=d.gI("ZERK").value;BuffPATK=BuffPATK*ZERK[bu];BuffPDEF=BuffPDEF*ZERKPDEF[bu];BuffMATK=BuffMATK*ZERKMATK[bu];BuffMDEF=BuffMDEF*ZERKMDEF[bu];BuffEVA=BuffEVA+ZERKEVA[bu];AddSPEED=AddSPEED+ZERKSPEED[bu];BuffASPD=BuffASPD*ZERK[bu];BuffCAST=BuffCAST*ZERK[bu]}
//Bless Shield
	if (d.gI("BSDC").checked==true)
		{var bu=d.gI("BSD").value;BuffShieldBlockRate=BuffShieldBlockRate*BLESSSHIELD[bu]}
//Bless the Body
	if (d.gI("BTB1C").checked==true)
		{var bu=d.gI("BTB1").value
		BuffHP=BuffHP*BTB[bu]}
	if (d.gI("BTB2C").checked==true)
		{var bu=d.gI("BTB2").value
		BuffHP=BuffHP*BTB[bu]}
	if (d.gI("BTB3C").checked==true)
		{var bu=d.gI("BTB3").value
		BuffHP=BuffHP*BTB[bu]}
//Bless the Soul
	if (d.gI("BTSC").checked==true)
		{var bu=d.gI("BTS").value;BuffMP=BuffMP*BTS[bu]}
//Blessing of Prophecy
	if (d.gI("BOPC").checked==true && (ClassType=="F1"||ClassType=="F2"||ClassType=="F3")) 
		{weightlimit=weightlimit*1.25}
//Blessing of Seraphim
	if (d.gI("SERC").checked==true)
		{var bu=d.gI("SER").value;BuffMPR=BuffMPR*SERAPHIM[bu]}
//Blinding Blow
	if (d.gI("BLDC").checked==true)
		{BuffSPEED=BuffSPEED*1.4}
//Block Shield
	if (d.gI("BLKSC").checked==true)
		{BuffPDEF=0.90*BuffPDEF}
//Block Wind Walk
	if (d.gI("BWWC").checked==true)
		{BuffSPEED=BuffSPEED*0.9}
//Casting Spd. Debuffs
	if (d.gI("DECC").checked==true)
		{BuffCAST=BuffCAST*0.77}
//Curse Gloom
	if (d.gI("GLMC").checked==true)
		{BuffMDEF=0.77*BuffMDEF}
//Curse of Abyss
	if (d.gI("CBYC").checked==true)
		{BuffMATK=0.70*BuffMATK;BuffEVA=BuffEVA-6;BuffSPEED=BuffSPEED*0.9;BuffCAST=BuffCAST*0.8}
//Curse of Doom
	if (d.gI("CDMC").checked==true)
		{BuffPATK=0.83*BuffPATK}
//Curse of Shade
	if (d.gI("CSHC").checked==true)
		{var bu=d.gI("CSH").value;BuffPDEF=CURSEOFSHADE[bu]*BuffPDEF;BuffMDEF=CURSEOFSHADE[bu]*BuffMDEF}
//Dance of Concentration
	if (d.gI("DCONC").checked==true)
		{BuffCAST=BuffCAST*1.3}
//Dance of Fire
	if (d.gI("DOFC").checked==true)
		{BuffCRITDMG=BuffCRITDMG*1.35}
//Dance of Fury
	if (d.gI("DFUC").checked==true)
		{BuffASPD=BuffASPD*1.15}
//Dance of Inspiration
	if (d.gI("DOIC").checked==true)
		{BuffACC=BuffACC+4}
//Dance of Mystic
	if (d.gI("DOMYC").checked==true)
		{BuffMATK=1.2*BuffMATK}
//Dance of Shadow
	if (d.gI("DSHC").checked==true)
		{BuffSPEED=BuffSPEED*0.5}
//Dance of Vampire
	if (d.gI("DOVC").checked==true)
		{BuffVR=BuffVR+0.08}
//Dance of Warrior
	if (d.gI("DWAC").checked==true)
		{BuffPATK=1.12*BuffPATK}
//Dark Vortex
	if (d.gI("DVC").checked==true)
		{AddMPR2=AddMPR2-12}
//Dash
	if (d.gI("DAS1C").checked==true)
		{var bu=d.gI("DAS1").value;BuffSPEED=BuffSPEED*DASH[bu]}
	if (d.gI("DAS2C").checked==true)
		{var bu=d.gI("DAS2").value;BuffSPEED=BuffSPEED*DASH[bu]}
//Dead Eye
	if (d.gI("DEYC").checked==true && WpnMAS=="Bow")
		{var bu=d.gI("DEY").value;AddPATK=AddPATK+DEADEYEPATK[bu];BuffACC=BuffACC+DEADEYEACC[bu];BuffASPD=BuffASPD*0.8;BuffCRITDMG=BuffCRITDMG*1.2}
//Death Whisper
	if (d.gI("DEWC").checked==true)
		{var bu=d.gI("DEW").value;BuffCRITDMG=BuffCRITDMG*DEATHWHISPER[bu]}
//Decrease Weight
	if (d.gI("DCWC").checked==true)
		{var bu=d.gI("DCW").value;weightpenalty=weightpenalty+DECREASEWEIGHT[bu]}
//Deflect Arrow
	if (d.gI("DEA1C").checked==true && (EWpnType=="Bow"||EWpnType=="YumiBow"))
		{var defarrow=d.gI("DEA1").value;EBuffDAMAGE=EBuffDAMAGE*DEFLECTARROW[defarrow]}
	if (d.gI("DEA2C").checked==true && (EWpnType=="Bow"||EWpnType=="YumiBow"))
		{var dfarrow=d.gI("DEA2").value;EBuffDAMAGE=EBuffDAMAGE*DEFLECTARROW[dfarrow]}
//Demonic Blade Dance
	if (d.gI("DBDC").checked==true)
		{var bu=d.gI("DBD").value;BuffPATK=BuffPATK*DEMONICBD[bu];BuffMATK=BuffMATK*DEMONICBD[bu]}
//Duelist Spirit
	if (d.gI("DUELC").checked==true && WpnType=="Duals")
		{var bu=d.gI("DUEL").value;BuffASPD=BuffASPD*DUELIST[bu]}
//Empower
	if (d.gI("EMPC").checked==true)
		{var bu=d.gI("EMP").value;BuffMATK=BuffMATK*EMPOWER[bu]}
//Evade Shot
	if (d.gI("EVSC").checked==true)
		{BuffEVA=BuffEVA+6}
//Fire Vortex
	if (d.gI("FVC").checked==true)
		{BuffSPEED=BuffSPEED*0.9;BuffASPD=BuffASPD*0.7;BuffCAST=BuffCAST*0.9;AddMPR2=AddMPR2-12}
//Fist Fury
	if (d.gI("FFYC").checked==true)
		{BuffASPD=BuffASPD*1.25}
//Focus
	if (d.gI("FCSC").checked==true)
		{var bu=d.gI("FCS").value;subcritical=subcritical+(basecritical*FOCUS[bu])}
//Focus Attack
	if (d.gI("FAC").checked==true && WpnType=="Polearm")
		{var bu=d.gI("FA").value;BuffACC=BuffACC+FOCUSATTACK[bu];BuffCRITDMG=BuffCRITDMG*1.1}
//Focus Dagger Skills
	if (d.gI("FAWC").checked==true && WpnMAS=="Dagger")
		{var bu=d.gI("FAW").value;
		if (bu==2)
			{if (position==1)
				{subcritical=subcritical+(basecritical*-0.3);BuffCRITDMG=BuffCRITDMG*0.7}
			if (position==2||position==4)
				{subcritical=subcritical+(basecritical*-0.3)}
			if (position==3||position==5)
				{subcritical=subcritical+(basecritical*-0.3);BuffCRITDMG=BuffCRITDMG*1.9}}
		if (bu==3)
			{if (position==1)
				{BuffCRITDMG=BuffCRITDMG*0.7}
			if (position==2||position==4)
				{BuffCRITDMG=BuffCRITDMG*1.3}
			if (position==3||position==5)
				{BuffCRITDMG=BuffCRITDMG*1.6}}}
	if (d.gI("FPWC").checked==true && WpnMAS=="Dagger")
		{var bu=d.gI("FPW").value;
		if (bu==1)
			{if (position==1)
				{BuffCOMBATCRIT=BuffCOMBATCRIT*0.7}
			if (position==2||position==4)
				{BuffCOMBATCRIT=BuffCOMBATCRIT*1.3}
			if (position==3||position==5)
				{BuffCOMBATCRIT=BuffCOMBATCRIT*1.6}}
		if (bu==2)
			{if (position==1)
				{subcritical=subcritical+(basecritical*-0.3);BuffCRITDMG=BuffCRITDMG*0.7}
			if (position==2||position==4)
				{subcritical=subcritical+(basecritical*-0.3)}
			if (position==3||position==5)
				{subcritical=subcritical+(basecritical*-0.3);BuffCRITDMG=BuffCRITDMG*1.9}}}
	if (d.gI("FTHC").checked==true && WpnMAS=="Dagger")
		{var bu=d.gI("FTH").value;
		if (bu==1)
			{if (position==1)
				{BuffCOMBATCRIT=BuffCOMBATCRIT*0.7}
			if (position==2||position==4)
				{BuffCOMBATCRIT=BuffCOMBATCRIT*1.3}
			if (position==3||position==5)
				{BuffCOMBATCRIT=BuffCOMBATCRIT*1.6}}
		if (bu==3)
			{if (position==1)
				{BuffCRITDMG=BuffCRITDMG*0.7}
			if (position==2||position==4)
				{BuffCRITDMG=BuffCRITDMG*1.3}
			if (position==3||position==5)
				{BuffCRITDMG=BuffCRITDMG*1.6}}}
//Frenzy
	if (d.gI("FZY1C").checked==true && (WpnType=="2hs"||WpnType=="2hb"))
		{var bu=d.gI("FZY1").value
		BuffPATK=BuffPATK*FRENZY2HS[bu]
		BuffACC=BuffACC+FRENZY2HSACC[bu]}
	else if (d.gI("FZY1C").checked==true && (WpnType=="Sword"||WpnType=="Blunt"||WpnType=="Polearm"))
		{var bu=d.gI("FZY1").value;BuffPATK=BuffPATK*FRENZY[bu]}
	else if (d.gI("FZY1C").checked==true)
		{var bu=d.gI("FZY1").value;BuffPATK=BuffPATK*FRENZYOTHER[bu]}
	if (d.gI("FZY2C").checked==true && (WpnType=="2hs"||WpnType=="2hb"))
		{var bu=d.gI("FZY2").value
		BuffPATK=BuffPATK*FRENZY2HS[bu]
		BuffACC=BuffACC+FRENZY2HSACC[bu]}
	else if (d.gI("FZY2C").checked==true && (WpnType=="Sword"||WpnType=="Blunt"||WpnType=="Polearm"))
		{var bu=d.gI("FZY2").value;BuffPATK=BuffPATK*FRENZY[bu]}
	else if (d.gI("FZY2C").checked==true)
		{var bu=d.gI("FZY2").value;BuffPATK=BuffPATK*FRENZYOTHER[bu]}
//Greater Might
	if (d.gI("GRMC").checked==true)
		{var bu=d.gI("GRM").value;BuffPATK=BuffPATK*GREATERMIGHT[bu]}
//Greater Shield
	if (d.gI("GRSC").checked==true)
		{var bu=d.gI("GRS").value;BuffPDEF=BuffPDEF*GREATERSHIELD[bu]}
//Guard Stance
	if (d.gI("GSC").checked==true)
		{var bu=d.gI("GS").value;AddPDEF=AddPDEF+GUARDSTANCE[bu];BuffShieldBlockRate=BuffShieldBlockRate*1.5}
//Guidance
	if (d.gI("GUIDC").checked==true)
		{var bu=d.gI("GUID").value;BuffACC=BuffACC+GUIDANCE[bu]}
//Guts
	if (d.gI("GTS1C").checked==true)
		{var bu=d.gI("GTS1").value;BuffPDEF=BuffPDEF*GUTS[bu]}
	if (d.gI("GTS2C").checked==true)
		{var bu=d.gI("GTS2").value;BuffPDEF=BuffPDEF*GUTS[bu]}
//Haste
	if (d.gI("HSTC").checked==true)
		{var bu=d.gI("HST").value;BuffASPD=BuffASPD*HASTE[bu]}
//Hawk Eye
	if (d.gI("HEC").checked==true)
		{var bu=d.gI("HE").value;BuffPDEF=0.90*BuffPDEF;BuffACC=BuffACC+HAWKEYE[bu]}
//Hero Debuffs
	if (d.gI("HEEC").checked==true) {
		var bu=d.gI("HEE").value;
		if (bu=="1")
			{BuffPDEF=BuffPDEF*0.5;AddMDEF=AddMDEF-50;BuffEVA=BuffEVA-16;BuffShieldBlockRate=BuffShieldBlockRate*0.5}
	}
	if (d.gI("HEDC").checked==true) {
		var bu=d.gI("HED").value;
		if (bu=="1")
			{BuffSPEED=BuffSPEED*1.52}
	}
//Hero Skills
	if (d.gI("HERC").checked==true) {
		var bu=d.gI("HER").value;
		if (bu=="1")
			{AddPATK=AddPATK+500;AddMATK=AddMATK+500;BuffPDEF=BuffPDEF*0.75;AddMDEF=AddMDEF-25;BuffACC=BuffACC+8;BuffEVA=BuffEVA-8;AddSPEED=AddSPEED+20;AddASPD=AddASPD+100;AddCAST=AddCAST+100}
		else if (bu=="2" && shield!=0)
			{AddPDEF=AddPDEF+5400;AddMDEF=AddMDEF+4050;AddSPEED=AddSPEED+5}
		else if (bu=="3")
			{AddPATK=AddPATK+250;AddPDEF=AddPDEF+500}
	}
//Hot Springs Cholera
	if (d.gI("CHOLC").checked==true)
		{var bu=d.gI("CHOL").value;BuffACC=BuffACC+HSCHOLACC[bu];BuffEVA=BuffEVA+HSCHOLEVA[bu]}
//Hot Springs Malaria
	if (d.gI("MALC").checked==true)
		{var bu=d.gI("MAL").value;BuffCAST=BuffCAST*HSMALARIA[bu]}
//Ice Vortex
	if (d.gI("IVC").checked==true)
		{var iv=d.gI("IV").value;BuffSPEED=BuffSPEED*0.7;BuffASPD=BuffASPD*0.9;BuffCAST=BuffCAST*0.9;AddMPR2=AddMPR2-12}
//Invocation
	if (d.gI("INVC").checked==true)
		{BuffPDEF=BuffPDEF*0.1}
//Judgment
	if (d.gI("JDGC").checked==true)
		{var iv=d.gI("JDG").value;BuffCRITDMG=BuffCRITDMG*JUDGMENT[iv]}
//Light Vortex
	if (d.gI("LVORC").checked==true)
		{var bu=d.gI("LVOR").value;BuffACC=BuffACC-6;AddMPR2=AddMPR2-12}
//Magic Barrier
	if (d.gI("MB1C").checked==true)
		{var mb1=d.gI("MB1").value;BuffMDEF=BuffMDEF*MAGICBARRIER[mb1]}
	if (d.gI("MB2C").checked==true)
		{var mb2=d.gI("MB2").value;BuffMDEF=BuffMDEF*MAGICBARRIER[mb2]}
	if (d.gI("MB3C").checked==true)
		{var mb3=d.gI("MB3").value;BuffMDEF=BuffMDEF*MAGICBARRIER[mb3]}
//Majesty
	if (d.gI("MAJ1C").checked==true)
		{var maj1=d.gI("MAJ1").value;BuffPDEF=BuffPDEF*MAJESTY[maj1];BuffEVA=BuffEVA+MAJESTYEVA[maj1]}
	if (d.gI("MAJ2C").checked==true)
		{var maj2=d.gI("MAJ2").value;BuffPDEF=BuffPDEF*MAJESTY[maj2];BuffEVA=BuffEVA+MAJESTYEVA[maj2]}
//Mana Regeneration
	if (d.gI("MRGC").checked==true)
		{var manaregen=d.gI("MRG").value;AddMPR=AddMPR+MANAREGEN[manaregen]}
//Might
	if (d.gI("MT1C").checked==true)
		{var bu=d.gI("MT1").value;BuffPATK=BuffPATK*MIGHT[bu]}
	if (d.gI("MT2C").checked==true)
		{var bu=d.gI("MT2").value;BuffPATK=BuffPATK*MIGHT[bu]}
//Pa'agrio's Fist
	if (d.gI("PAFC").checked==true)
		{AddCP=AddCP+800}
//Parry Stance
	if (d.gI("PRYC").checked==true)
		{var parry=d.gI("PRY").value;BuffPDEF=1.25*BuffPDEF;BuffMDEF=1.25*BuffMDEF;BuffACC=BuffACC-4;BuffSPEED=BuffSPEED*0.9;BuffASPD=BuffASPD*0.8}
//P.Atk. Debuffs
	if (d.gI("DEPC").checked==true)
		{var depatk=d.gI("DEP").value;BuffPATK=BuffPATK*WEAKNESS[depatk]}
//P.Def. Debuffs
	if (d.gI("DEDC").checked==true)
		{var depdef=d.gI("DED").value;BuffPDEF=BuffPDEF*HEX[depdef]}
//Polearm Accuracy
	if (d.gI("PACC").checked==true)
		{var poleacc=d.gI("PAC").value;BuffACC=BuffACC+POLEACC[poleacc]}
//Prophecies
	var proph=d.gI("PRP").value
	if (d.gI("PRPC").checked==true)
		{BuffHP=BuffHP*PROPHECYHP[proph];
		BuffMP=BuffMP*PROPHECYMP[proph];
		BuffPATK=BuffPATK*PROPHECYPATK[proph];
		BuffMATK=BuffMATK*PROPHECYMATK[proph];
		BuffPDEF=BuffPDEF*PROPHECYPDEF[proph];
		BuffMDEF=BuffMDEF*PROPHECYMDEF[proph];
		BuffACC=BuffACC+PROPHECYACC[proph];
		BuffEVA=BuffEVA+PROPHECYEVA[proph];
		subcritical=subcritical+(basecritical*PROPHECYCRIT[proph]);
		BuffSPEED=BuffSPEED*PROPHECYSPEED[proph];
		BuffASPD=BuffASPD*PROPHECYASPD[proph];
		BuffCAST=BuffCAST*PROPHECYCAST[proph];
		BuffHPR=BuffHPR*PROPHECYHPR[proph];
		BuffMPR=BuffMPR*PROPHECYMPR[proph];
		BuffCRITDMG=BuffCRITDMG*PROPHECYCRITDMG[proph]
		BuffVR=BuffVR+PROPHECYVR[proph]}
	if (d.gI("PRPC").checked==true && proph==4 && position==3)
		{BuffCRITDMG=BuffCRITDMG*1.2;BuffCOMBATCRIT=BuffCOMBATCRIT*1.2}
//Psycho Symphony
	if (d.gI("PSYC").checked==true)
		{var psycho=d.gI("PSY").value;
		BuffSPEED=BuffSPEED*PSYCHO[psycho];
		BuffASPD=BuffASPD*PSYCHO[psycho];
		BuffCAST=BuffCAST*PSYCHO[psycho];}
//Queen Buffs
	var queen=d.gI("QUEE").value
	if ((queen=="1"||queen=="2"||queen=="3") && d.gI("QUEEC").checked==true)
		{BuffPATK=CATBUFFS[queen]*BuffPATK;BuffACC=BuffACC+2}
	else if ((queen=="4"||queen=="5"||queen=="6") && d.gI("QUEEC").checked==true)
		{subcritical=subcritical+(basecritical*CATBUFFS[queen]);BuffCRITDMG=BuffCRITDMG*CATBUFFS2[queen]}
//Rage
	if (d.gI("RGE1C").checked==true && (WpnType=="2hs"||WpnType=="2hb"))
		{var rage1=d.gI("RGE1").value;BuffPATK=RAGE2HS[rage1]*BuffPATK;BuffPDEF=0.80*BuffPDEF;BuffEVA=BuffEVA-3;BuffACC=BuffACC+RAGE2HSACC[rage1]}
	else if (d.gI("RGE1C").checked==true && (WpnType=="Sword"||WpnType=="Blunt"||WpnType=="Polearm"))
		{var rage1=d.gI("RGE1").value;BuffPATK=RAGE[rage1]*BuffPATK;BuffPDEF=0.80*BuffPDEF;BuffEVA=BuffEVA-3}
	else if (d.gI("RGE1C").checked==true)
		{var rage1=d.gI("RGE1").value;BuffPATK=RAGEOTHER[rage1]*BuffPATK;BuffPDEF=0.80*BuffPDEF;BuffEVA=BuffEVA-3}
	if (d.gI("RGE2C").checked==true && (WpnType=="2hs"||WpnType=="2hb"))
		{var rage2=d.gI("RGE2").value;BuffPATK=RAGE2HS[rage2]*BuffPATK;BuffPDEF=0.80*BuffPDEF;BuffEVA=BuffEVA-3;BuffACC=BuffACC+RAGE2HSACC[rage2]}
	else if (d.gI("RGE2C").checked==true && (WpnType=="Sword"||WpnType=="Blunt"||WpnType=="Polearm"))
		{var rage2=d.gI("RGE2").value;BuffPATK=RAGE[rage2]*BuffPATK;BuffPDEF=0.80*BuffPDEF;BuffEVA=BuffEVA-3}
	else if (d.gI("RGE2C").checked==true && (WpnType!="2hs" && WpnType!="2hb"))
		{var rage2=d.gI("RGE2").value;BuffPATK=RAGEOTHER[rage2]*BuffPATK;BuffPDEF=0.80*BuffPDEF;BuffEVA=BuffEVA-3}
//Rapid Fire
	if (d.gI("RPFC").checked==true && WpnMAS=="Bow")
		{var rapidfire=d.gI("RPF").value
		AddPATK=AddPATK+RAPIDFIRE[rapidfire];
		BuffASPD=BuffASPD*1.2}
//Rapid Shot
	if (d.gI("RPD1C").checked==true && WpnMAS=="Bow")
		{var rapid1=d.gI("RPD1").value;BuffASPD=BuffASPD*RAPIDSHOT[rapid1]}
	if (d.gI("RPD2C").checked==true && WpnMAS=="Bow")
		{var rapid2=d.gI("RPD2").value;BuffASPD=BuffASPD*RAPIDSHOT[rapid2]}
//Regeneration
	if (d.gI("RGNC").checked==true)
		{var regeneration=d.gI("RGN").value;BuffHPR=BuffHPR*REGENERATION[regeneration]}
//Riposte Stance
	if (d.gI("RIPC").checked==true)
		{var riposte=d.gI("RIP").value;BuffACC=BuffACC-4;BuffSPEED=BuffSPEED*0.9;BuffASPD=BuffASPD*0.8;BuffRD=BuffRD+0.3}
//Sand Bomb
	if (d.gI("SBBC").checked==true)
		{var sandbomb=d.gI("SBB").value;BuffACC=BuffACC+SANDBOMB[sandbomb]}
//Seal of Despair
	if (d.gI("DERC").checked==true)
		{var despair=d.gI("DER").value;BuffMDEF=0.70*BuffMDEF;BuffACC=BuffACC-6;subcritical=subcritical+(basecritical*-0.3);BuffSPEED=BuffSPEED*0.8;BuffASPD=BuffASPD*0.8;BuffCRITDMG=BuffCRITDMG*0.7}
//Seal of Scourge
	if (d.gI("SCRC").checked==true)
		{BuffHPR2=BuffHPR2*0}
//Shield
	if (d.gI("SHD1C").checked==true)
		{var shield1=d.gI("SHD1").value;BuffPDEF=BuffPDEF*SHIELD[shield1]}
	if (d.gI("SHD2C").checked==true)
		{var shield2=d.gI("SHD2").value;BuffPDEF=BuffPDEF*SHIELD[shield2]}
//Shield Fortress
	if (d.gI("SFC").checked==true && shield!="0")
		{var shieldfortress=d.gI("SF").value;AddSHIELDPDEF=AddSHIELDPDEF+SHIELDFORTRESS[shieldfortress]}
//Shock Blast
	if (d.gI("SBC").checked==true)
		{var shockblast=d.gI("SB").value;BuffPDEF=0.70*BuffPDEF;BuffMDEF=0.70*BuffMDEF}
//Silent Move
	if (d.gI("SMC").checked==true)
		{var sm=d.gI("SM").value;BuffSPEED=BuffSPEED*0.6}
//Skin
	if (d.gI("SKNC").checked==true)
		{var bu=d.gI("SKN").value;BuffRD=BuffRD+SKIN[bu]}
//Slow
	if (d.gI("DESC").checked==true)
		{var bu=d.gI("DES").value;BuffSPEED=BuffSPEED*SLOW[bu]}
//Snipe
	if (d.gI("SNIC").checked==true && WpnMAS=="Bow")
		{var snipe=d.gI("SNI").value;AddPATK=AddPATK+SNIPE[snipe];BuffACC=BuffACC+SNIPEACC[snipe];subcritical=subcritical+(basecritical*0.2)}
//Song of Earth
	if (d.gI("SOEC").checked==true)
		{BuffPDEF=1.25*BuffPDEF}
//Song of Hunter
	if (d.gI("SOHC").checked==true)
		{subcritical=subcritical+(basecritical*1)}
//Song of Life
	if (d.gI("SOLC").checked==true)
		{BuffHPR=BuffHPR*1.2}
//Song of Meditation
	if (d.gI("SOMC").checked==true)
		{BuffMPR=BuffMPR*1.2}
//Song of Vengeance
	if (d.gI("SOVC").checked==true)
		{BuffRD=BuffRD+0.2}
//Song of Vitality
	if (d.gI("VITC").checked==true)
		{BuffHP=1.3*BuffHP}
//Song of Warding
	if (d.gI("WARDC").checked==true)
		{BuffMDEF=1.3*BuffMDEF}
//Song of Water
	if (d.gI("SWATC").checked==true)
		{BuffEVA=BuffEVA+3}
//Song of Wind
	if (d.gI("SWIC").checked==true)
		{AddSPEED=AddSPEED+20}
//Soul Cry
	if (d.gI("SL1C").checked==true)
		{var soul1=d.gI("SL1").value;AddPATK=AddPATK+SOULCRY[soul1]}
	if (d.gI("SL2C").checked==true)
		{var soul2=d.gI("SL2").value;AddPATK=AddPATK+SOULCRY[soul2]}
	if (d.gI("SL3C").checked==true)
		{var soul3=d.gI("SL3").value;AddPATK=AddPATK+SOULCRY[soul3]}
//Soul Guard
	if (d.gI("SGC").checked==true)
		{var sg=d.gI("SG").value;AddPDEF=AddPDEF+SOULGUARD[sg]}
//Soul of Sagitarrius
	if (d.gI("SAGC").checked==true)
		{var sag=d.gI("SAG").value;BuffMP=BuffMP*SOULOFSAG[sag]}
//Stealth
	if (d.gI("STLC").checked==true)
		{var stealth=d.gI("STL").value
		BuffPATK=BuffPATK*0.55;
		BuffPDEF=BuffPDEF*STEALTH[stealth];
		BuffMDEF=BuffMDEF*STEALTH[stealth];
		BuffACC=BuffACC-12;
		BuffEVA=BuffEVA+STEALTHEVA[stealth]}
//Thrill Fight
	if (d.gI("TFC").checked==true)
		{var tf=d.gI("TF").value;BuffSPEED=BuffSPEED*0.8;BuffASPD=BuffASPD*THRILLFIGHT[tf]}
//Totem
	var totem1=d.gI("TTM1").value
	if (totem1=="1" && d.gI("TTM1C").checked==true)
		{BuffSPEED=BuffSPEED*0.7}
	if (totem1=="1" && d.gI("TTM1C").checked==true && WpnMAS=="Fist")
		{BuffPATK=1.2*BuffPATK}
	else if (totem1=="2" && d.gI("TTM1C").checked==true)
		{BuffSPEED=BuffSPEED*1.2;BuffACC=BuffACC+3}
	var totem2=d.gI("TTM2").value
	if (totem2=="1" && d.gI("TTM2C").checked==true)
		{BuffSPEED=BuffSPEED*0.7}
	if (totem2=="1" && d.gI("TTM2C").checked==true && WpnMAS=="Fist")
		{BuffPATK=1.2*BuffPATK;BuffCRITDMG=BuffCRITDMG*1.2}
	else if (totem2=="3" && d.gI("TTM2C").checked==true)
		{BuffPATK60=1.43*BuffPATK60;AddCRIT60=AddCRIT60+100;AddCRIT30=AddCRIT30+300;BuffACC=BuffACC+6}
	else if (totem2=="4" && d.gI("TTM2C").checked==true && WpnMAS=="Fist")
		{AddCRIT=AddCRIT+100;BuffACC=BuffACC+6;BuffCRITDMG=BuffCRITDMG*1.3}
	else if (totem2=="5" && d.gI("TTM2C").checked==true)
		{BuffMDEF=1.3*BuffMDEF;BuffPDEF=1.3*BuffPDEF;BuffEVA=BuffEVA-9;BuffSPEED=BuffSPEED*0.7}
	else if (totem2=="6" && d.gI("TTM2C").checked==true && WpnMAS=="Fist")
		{BuffACC=BuffACC+6;BuffASPD=BuffASPD*1.2}
	else if (totem2=="7" && d.gI("TTM2C").checked==true)
		{BuffPATK=0.70*BuffPATK;BuffEVA=BuffEVA+12;BuffSPEED=BuffSPEED*1.3;BuffACC=BuffACC-9}
	else if (totem2=="2" && d.gI("TTM2C").checked==true)
		{BuffSPEED=BuffSPEED*1.2;BuffACC=BuffACC+3}
//Tribunal
	if (d.gI("TRBC").checked==true)
		{var tribunal=d.gI("TRB").value;subcritical=subcritical+(basecritical*TRIBUNAL[tribunal])}
//Ultimate Defense
	if (d.gI("UD1C").checked==true)
		{var ud1=d.gI("UD1").value
		AddPDEF=AddPDEF+UDPDEF[ud1]
		AddMDEF=AddMDEF+UDMDEF[ud1]}
	if (d.gI("UD2C").checked==true)
		{var ud2=d.gI("UD2").value
		AddPDEF=AddPDEF+UDPDEF[ud2]
		AddMDEF=AddMDEF+UDMDEF[ud2]}
//Ultimate Evasion
	if (d.gI("UEC").checked==true)
		{var ue=d.gI("UE").value;BuffEVA=BuffEVA+UE[ue]}
//Vampiric Rage
	if (d.gI("VRAC").checked==true)
		{var bu=d.gI("VRA").value;BuffVR=BuffVR+VAMPIRICRAGE[bu]}
//Vicious Stance
	if (d.gI("VSC").checked==true)
		{var ue=d.gI("VS").value;AddCRITDMG=AddCRITDMG+VICIOUSSTANCE[ue]}
//War Cry
	if (d.gI("WCY1C").checked==true)
		{var warcry1=d.gI("WCY1").value;BuffPATK=BuffPATK*WARCRY[warcry1]}
	if (d.gI("WCY2C").checked==true)
		{var warcry2=d.gI("WCY2").value;BuffPATK=BuffPATK*WARCRY[warcry2]}
//Wind Vortex
	if (d.gI("WVC").checked==true)
		{var wv=d.gI("WV").value;BuffSPEED=BuffSPEED*0.9;BuffASPD=BuffASPD*0.9;BuffCAST=BuffCAST*0.7;AddMPR2=AddMPR2-12}
//Wind Walk
	if (d.gI("WWC").checked==true)
		{var ww=d.gI("WW").value;AddSPEED=AddSPEED+WINDWALK[ww]}
//Zealot
	if (d.gI("ZLTC").checked==true)
		{var zealot=d.gI("ZLT").value
		AddSPEED=AddSPEED+ZEALOTSPEED[zealot]}
	if (d.gI("ZLTC").checked==true && (WpnType=="Fist"||WpnType=="2hb"||WpnType=="Blunt"))
		{var zealot=d.gI("ZLT").value
		BuffASPD=BuffASPD*ZEALOT[zealot]
		BuffACC=BuffACC+6
		BuffCRITDMG=BuffCRITDMG*ANGELICCRITDMG[zealot]}
	if (d.gI("ZLTC").checked==true && JOB=="DE" && (WpnType=="Polearm"||WpnType=="2hs"||WpnType=="Sword"))
		{var zealot=d.gI("ZLT").value
		AddCRIT=AddCRIT+ZEALOTCRIT[zealot]
		BuffASPD=BuffASPD*ZEALOT[zealot]
		BuffACC=BuffACC+6}

//Nuker Nerf
if (race=="HM"||race=="EM"||race=="OM"||race=="DM")
	{
	if (AType!="R")
	{BuffASPD=BuffASPD*0.8;BuffCAST=BuffCAST*0.5}
	else
	{BuffMPR=BuffMPR*1.2}
	if (WpnMAS=="Bow"||WpnType=="Polearm")
	{BuffACC=BuffACC-8;BuffCAST=BuffCAST*0.7}
	}
if (d.gI("HMO1C").checked==true && AType=="H"||d.gI("HMO2C").checked==true && AType=="H"||d.gI("HMO3C").checked==true && AType=="H")
	{BuffCAST=BuffCAST*1.71;BuffASPD=BuffASPD*1.25}
if (d.gI("LMO1C").checked==true && AType=="L"||d.gI("LMO2C").checked==true && AType=="L"||d.gI("LMO3C").checked==true && AType=="L")
	{BuffCAST=BuffCAST*1.9;BuffASPD=BuffASPD*1.25}
if (d.gI("LMSC").checked==true && AType=="L")
	{BuffCAST=BuffCAST*1.88;BuffASPD=BuffASPD*1.25}
if (d.gI("LMH1C").checked==true && AType=="L"||d.gI("LMH2C").checked==true && AType=="L")
	{BuffCAST=BuffCAST*1.91;BuffASPD=BuffASPD*1.25}
if (d.gI("HMPC").checked==true && AType=="H")
	{BuffCAST=BuffCAST*1.68;BuffASPD=BuffASPD*1.25}

//Passives
//Weapon Mastery for Fighter Classes
	if (d.gI("WMFC").checked==true)
		{var wmf=d.gI("WMF").value;AddPATK=AddPATK+WMF[wmf]}
//Weapon Mastery for Mystic Classes
	if (d.gI("WMM1C").checked==true)
		{var wmm1=d.gI("WMM1").value
		AddPATK=AddPATK+WMMPATK[wmm1];
		AddMATK=AddMATK+WMMMATK[wmm1]}
	if (d.gI("WMM2C").checked==true)
		{var wmm2=d.gI("WMM2").value
		AddPATK=AddPATK+WMMPATK[wmm2];
		AddMATK=AddMATK+WMMMATK[wmm2]}
	if (d.gI("WMM3C").checked==true)
		{var wmm3=d.gI("WMM3").value
		AddPATK=AddPATK+WMMPATK[wmm3];
		AddMATK=AddMATK+WMMMATK[wmm3]}
//Sword/Blunt Mastery (Warrior)
	if (d.gI("WSBM1C").checked==true && (WpnMAS=="Blunt"||WpnMAS=="Sword"))
		{var wsbm1=d.gI("WSBM1").value;AddPATK=AddPATK+MTYPE1[wsbm1]}
	if (d.gI("WSBM2C").checked==true && (WpnMAS=="Blunt"||WpnMAS=="Sword"))
		{var wsbm2=d.gI("WSBM2").value;AddPATK=AddPATK+MTYPE1[wsbm2]}
//Blunt Mastery
	if (d.gI("BLM1C").checked==true && WpnMAS=="Blunt")
		{var bluntm1=d.gI("BLM1").value
		AddPATK=AddPATK+MTYPE1[bluntm1];}
	if (d.gI("BLM2C").checked==true && WpnMAS=="Blunt")
		{var bluntm2=d.gI("BLM2").value
		AddPATK=AddPATK+MTYPE1[bluntm2];
		AddCRITDMG=AddCRITDMG+BLUNTMCRITDAMAGE[bluntm2]}
	if (d.gI("BLM2C").checked==true)
		{var bluntm2=d.gI("BLM2").value
		AddSPEED=AddSPEED+BLUNTMSPEED[bluntm2]}
//Polearm Mastery
	if (d.gI("PLM1C").checked==true && WpnMAS=="Polearm")
		{var polem1=d.gI("PLM1").value;AddPATK=AddPATK+MTYPE1[polem1]}
	if (d.gI("PLM2C").checked==true && WpnMAS=="Polearm")
		{var polem2=d.gI("PLM2").value;AddPATK=AddPATK+MTYPE1[polem2]}
//Fist Mastery
	if (d.gI("FIM1C").checked==true && WpnMAS=="Fist")
		{var fistm1=d.gI("FIM1").value;AddPATK=AddPATK+MTYPE1[fistm1]}
	if (d.gI("FIM2C").checked==true && WpnMAS=="Fist")
		{var fistm2=d.gI("FIM2").value;AddPATK=AddPATK+MTYPE1[fistm2]}
//Sword/Blunt Mastery (Knight)
	if  (d.gI("KSBM1C").checked==true && (WpnMAS=="Sword"||WpnMAS=="Blunt"))
		{var ksbm1=d.gI("KSBM1").value;AddPATK=AddPATK+KNIGHTMASTERY[ksbm1]}
	if  (d.gI("KSBM2C").checked==true && (WpnMAS=="Sword"||WpnMAS=="Blunt"))
		{var ksbm2=d.gI("KSBM2").value;AddPATK=AddPATK+KNIGHTMASTERY[ksbm2]}
//Dagger Mastery
	if (d.gI("DGM1C").checked==true && WpnMAS=="Dagger")
		{var daggerm1=d.gI("DGM1").value;AddPATK=AddPATK+DAGGERM[daggerm1]}
	if (d.gI("DGM2C").checked==true && WpnMAS=="Dagger")
		{var daggerm2=d.gI("DGM2").value;AddPATK=AddPATK+DAGGERM[daggerm2]}
//Bow Mastery
	if (d.gI("BOWM1C").checked==true && WpnMAS=="Bow")
		{var bowm1=d.gI("BOWM1").value;AddPATK=AddPATK+BOWM[bowm1]}
	if (d.gI("BOWM2C").checked==true && WpnMAS=="Bow")
		{var bowm2=d.gI("BOWM2").value;AddPATK=AddPATK+BOWM[bowm2]}
//Dual Weapon Mastery
	if (d.gI("DUALMC").checked==true && WpnMAS=="Duals")
		{var dualm=d.gI("DUALM").value;AddPATK=AddPATK+DUALM[dualm]}
//Two Handed Weapon Mastery
	if (d.gI("THM1C").checked==true && (WpnType=="2hs"||WpnType=="2hb"))
		{var thm1=d.gI("THM1").value
		AddPATK=AddPATK+THM[thm1];
		BuffACC=BuffACC+3}
	if (d.gI("THM2C").checked==true && (WpnType=="2hs"||WpnType=="2hb"))
		{var thm2=d.gI("THM2").value
		AddPATK=AddPATK+THM[thm2];
		BuffACC=BuffACC+3}
//Armor Mastery (Fighter)
	if (d.gI("AMFC").checked==true && AType=="L")
		{var amf=d.gI("AMF").value
		AddPDEF=AddPDEF+AMF[amf];
		BuffEVA=BuffEVA+AMFEVA[amf]}
	else if (d.gI("AMFC").checked==true && AType!="L")
		{var amf=d.gI("AMF").value
		AddPDEF=AddPDEF+AMF[amf];}
//Armor Mastery (Mystic)
	if (d.gI("AMMC").checked==true)
		{var amm=d.gI("AMM").value;AddPDEF=AddPDEF+AMM[amm]}
//Heavy Armor Mastery (Knight)
	if (d.gI("HMK1C").checked==true && AType=="H")
		{var hmk1=d.gI("HMK1").value;AddPDEF=AddPDEF+HMK[hmk1]}
	if (d.gI("HMK2C").checked==true && AType=="H")
		{var hmk2=d.gI("HMK2").value;AddPDEF=AddPDEF+HMK[hmk2]}
//Heavy Armor Mastery (Warrior)
	if (d.gI("HMW1C").checked==true && AType=="H")
		{var hmw1=d.gI("HMW1").valueAddPDEF=AddPDEF+HMW[hmw1]}
	if (d.gI("HMW2C").checked==true && AType=="H")
		{var hmw2=d.gI("HMW2").value;AddPDEF=AddPDEF+HMW[hmw2]}
//Heavy Armor Mastery (Orc Mystic)
	if (d.gI("HMO1C").checked==true && AType=="H")
		{var hmo1=d.gI("HMO1").value;AddPDEF=AddPDEF+HMO[hmo1]}
	if (d.gI("HMO2C").checked==true && AType=="H")
		{var hmo2=d.gI("HMO2").value;AddPDEF=AddPDEF+HMO[hmo2]}
	if (d.gI("HMO3C").checked==true && AType=="H")
		{var hmo3=d.gI("HMO3").value;AddPDEF=AddPDEF+HMO[hmo3]}
//Heavy Armor Mastery (Prophet)
	if (d.gI("HMPC").checked==true && AType=="H")
		{var hmp=d.gI("HMP").value;AddPDEF=AddPDEF+HMP[hmp]}
//Light Armor Mastery (Warrior)
	if (d.gI("LMW1C").checked==true && AType=="L")
		{var lmw1=d.gI("LMW1").value
		AddPDEF=AddPDEF+LMW[lmw1]
		BuffEVA=BuffEVA+LMWEVA[lmw1]}
	if (d.gI("LMW2C").checked==true && AType=="L")
		{var lmw2=d.gI("LMW2").value
		AddPDEF=AddPDEF+LMW[lmw2]
		BuffEVA=BuffEVA+LMWEVA[lmw2]}
//Light Armor Mastery (Rogue)
	if (d.gI("LMR1C").checked==true && AType=="L")
		{var lmr1=d.gI("LMR1").value
		AddPDEF=AddPDEF+LMR[lmr1]
		BuffEVA=BuffEVA+LMREVA[lmr1]}
	if (d.gI("LMR2C").checked==true && AType=="L")
		{var lmr2=d.gI("LMR2").value
		AddPDEF=AddPDEF+LMR[lmr2]
		BuffEVA=BuffEVA+LMREVA[lmr2]}
//Light Armor Mastery (Orc Mystic)
	if (d.gI("LMO1C").checked==true && AType=="L")
		{var lmo1=d.gI("LMO1").value;AddPDEF=AddPDEF+LMO[lmo1];BuffMPR=BuffMPR*1.2}
	if (d.gI("LMO2C").checked==true && AType=="L")
		{var lmo2=d.gI("LMO2").value;AddPDEF=AddPDEF+LMO[lmo2];BuffMPR=BuffMPR*1.2}
	if (d.gI("LMO3C").checked==true && AType=="L")
		{var lmo3=d.gI("LMO3").value;AddPDEF=AddPDEF+LMO[lmo3];BuffMPR=BuffMPR*1.2}
//Light Armor Mastery (Healer)
	if (d.gI("LMH1C").checked==true && AType=="L")
		{var lmh1=d.gI("LMH1").value;AddPDEF=AddPDEF+LMH[lmh1];BuffMPR=BuffMPR*1.2}
	if (d.gI("LMH2C").checked==true && AType=="L")
		{var lmh2=d.gI("LMH2").value;AddPDEF=AddPDEF+LMH[lmh2];BuffMPR=BuffMPR*1.2}
//Light Armor Mastery (Summoner)
	if (d.gI("LMSC").checked==true && AType=="L")
		{var lms=d.gI("LMS").value;AddPDEF=AddPDEF+LMS[lms];BuffMPR=BuffMPR*1.2}
//Robe Mastery (Nuker)
	if (d.gI("RMN1C").checked==true && AType=="R")
		{var rmn1=d.gI("RMN1").value;AddPDEF=AddPDEF+RMN[rmn1]}
	if (d.gI("RMN2C").checked==true && AType=="R")
		{var rmn2=d.gI("RMN2").value;AddPDEF=AddPDEF+RMN[rmn2]}
//Robe Mastery (Healer)
	if (d.gI("RMH1C").checked==true && AType=="R")
		{var rmh1=d.gI("RMH1").value;AddPDEF=AddPDEF+RMH[rmh1]}
	if (d.gI("RMH2C").checked==true && AType=="R")
		{var rmh2=d.gI("RMH2").value;AddPDEF=AddPDEF+RMH[rmh2]}
//Robe Mastery (Orc Mystic)
	if (d.gI("RMO1C").checked==true && AType=="R")
		{var rmo1=d.gI("RMO1").value;AddPDEF=AddPDEF+RMO[rmo1]}
	if (d.gI("RMO2C").checked==true && AType=="R")
		{var rmo2=d.gI("RMO2").value;AddPDEF=AddPDEF+RMO[rmo2]}
	if (d.gI("RMO3C").checked==true && AType=="R")
		{var rmo3=d.gI("RMO3").value;AddPDEF=AddPDEF+RMO[rmo3]}
//Magic Resistance
	if (d.gI("MR1C").checked==true)
		{var mr1=d.gI("MR1").value;AddMDEF=AddMDEF+MR[mr1]}
	if (d.gI("MR2C").checked==true)
		{var mr2=d.gI("MR2").value;AddMDEF=AddMDEF+MR[mr2]}
//Anti-Magic
	if (d.gI("AM1C").checked==true)
		{var am1=d.gI("AM1").value;AddMDEF=AddMDEF+AM[am1]}
	if (d.gI("AM2C").checked==true)
		{var am2=d.gI("AM2").value;AddMDEF=AddMDEF+AM[am2]}
	if (d.gI("AM3C").checked==true)
		{var am3=d.gI("AM3").value;AddMDEF=AddMDEF+AM[am3]}
//Acrobatic Move
	if (d.gI("ACRM1C").checked==true && movemode=="0.7")
		{var bu=d.gI("ACRM1").value;combatevasion=combatevasion+ACROBATICMOVE[bu]}
	if (d.gI("ACRM2C").checked==true && movemode=="0.7")
		{var bu=d.gI("ACRM2").value;combatevasion=combatevasion+ACROBATICMOVE[bu]}
//Aegis
	if (d.gI("AGSC").checked==true)
		{block360=true}
//Agile Movement
	if (d.gI("AGM1C").checked==true && AType=="L")
		{var agilemovement1=d.gI("AGM1").value
		BuffACC=BuffACC+AGILEMOVEMENT[agilemovement1]
		AddSPEED=AddSPEED+AGILEMOVEMENTSPEED[agilemovement1]}
	if (d.gI("AGM2C").checked==true && AType=="L")
		{var agilemovement2=d.gI("AGM2").value
		BuffACC=BuffACC+AGILEMOVEMENT[agilemovement2]
		AddSPEED=AddSPEED+AGILEMOVEMENTSPEED[agilemovement2]}
//Arcane Lore
	if (d.gI("ARLC").checked==true)
		{BuffMATK=BuffMATK*1.03}
//Archery
	if (d.gI("ARCC").checked==true && WpnMAS=="Bow")
		{AddPATK=AddPATK+200}
//Assassination
	if (d.gI("ASSC").checked==true && WpnType=="Dagger")
		{AddPATK=AddPATK+70}
//Boost Attack Speed
	if (d.gI("BOA1C").checked==true)
		{var boostaspd1=d.gI("BOA1").value;BuffASPD=BuffASPD*BOOSTASPD[boostaspd1]}
	if (d.gI("BOA2C").checked==true)
		{var boostaspd2=d.gI("BOA2").value;BuffASPD=BuffASPD*BOOSTASPD[boostaspd2]}
//Boost Evasion
	if (d.gI("BEV1C").checked==true)
		{var bu=d.gI("BEV1").value;BuffEVA=BuffEVA+BOOSTEVASION[bu]}
	if (d.gI("BEV2C").checked==true)
		{var bu=d.gI("BEV2").value;BuffEVA=BuffEVA+BOOSTEVASION[bu]}
//Boost HP
	if (d.gI("BHP1C").checked==true)
		{var boosthp1=d.gI("BHP1").value
		AddHP=AddHP+BOOSTHP[boosthp1]
		AddCP=AddCP+BOOSTHPCP[boosthp1]}
	if (d.gI("BHP2C").checked==true)
		{var boosthp2=d.gI("BHP2").value
		AddHP=AddHP+BOOSTHP[boosthp2]
		AddCP=AddCP+BOOSTHPCP[boosthp2]}
	if (d.gI("BHP3C").checked==true)
		{var boosthp3=d.gI("BHP3").value
		AddHP=AddHP+BOOSTHP[boosthp3]
		AddCP=AddCP+BOOSTHPCP[boosthp3]}
//Boost Mana
	if (d.gI("BMP1C").checked==true)
		{var boostmp1=d.gI("BMP1").value;AddMP=AddMP+BOOSTMP[boostmp1]}
	if (d.gI("BMP2C").checked==true)
		{var boostmp2=d.gI("BMP2").value;AddMP=AddMP+BOOSTMP[boostmp2]}
//Clan Aegis
	if (d.gI("CLPDC").checked==true)
		{var clanpdef=d.gI("CLPD").value;BuffPDEF=BuffPDEF*CLANMULTIPLY[clanpdef]}
//Clan Agility
	if (d.gI("CLAGC").checked==true)
		{var bu=d.gI("CLAG").value;BuffEVA=BuffEVA+CLANADDB[bu]}
//Clan Empowerment
	if (d.gI("CLEMC").checked==true)
		{var bu=d.gI("CLEM").value;BuffMATK=BuffMATK*1.06}
//Clan Guidance
	if (d.gI("CLGDC").checked==true)
		{var clanguidance=d.gI("CLGD").value;BuffACC=BuffACC+CLANADDB[clanguidance]}
//Clan Lifeblood
	if (d.gI("CLHRC").checked==true)
		{AddHPR=AddHPR+0.5*movemode}
//Clan Magic Protection
	if (d.gI("CLMDC").checked==true)
		{var clanmdef=d.gI("CLMD").value;BuffMDEF=BuffMDEF*CLANMULTIPLYB[clanmdef]}
//Clan March
	if (d.gI("CLSPC").checked==true)
		{var clanspeed=d.gI("CLSP").value;AddSPEED=AddSPEED+CLANADD[clanspeed]}
//Clan Might
	if (d.gI("CLPAC").checked==true)
		{var clanpatk=d.gI("CLPA").value;BuffPATK=BuffPATK*CLANMULTIPLY[clanpatk]}
//Clan Spirituality
	if (d.gI("CLCPC").checked==true)
		{var clancp=d.gI("CLCP").value;BuffCP=BuffCP*CLANMULTIPLYB[clancp]}
//Clan Vitality
	if (d.gI("CLHPC").checked==true)
		{var clanhp=d.gI("CLHP").value;BuffHP=BuffHP*CLANMULTIPLY[clanhp]}
//Clear Mind
	if (d.gI("CLMC").checked==true && movemode==1)
		{var clearmind=d.gI("CLM").value;AddMPR=AddMPR+CLEARMINDWALK[clearmind]}
	if (d.gI("CLMC").checked==true && movemode==1.1)
		{var clearmind=d.gI("CLM").value;AddMPR=AddMPR+CLEARMINDSTAND[clearmind]}
//Critical Chance
	if (d.gI("CRC1C").checked==true)
		{var criticalchance1=d.gI("CRC1").value;subcritical=subcritical+(basecritical*CRITICALCHANCE[criticalchance1])}
	if (d.gI("CRC2C").checked==true)
		{var criticalchance2=d.gI("CRC2").value;subcritical=subcritical+(basecritical*CRITICALCHANCE[criticalchance2])}
//Critical Power
	if (d.gI("CRP1C").checked==true)
		{var bu=d.gI("CRP1").value;AddCRITDMG=AddCRITDMG+CRITICALPOWER[bu]}
	if (d.gI("CRP2C").checked==true)
		{var bu=d.gI("CRP2").value;AddCRITDMG=AddCRITDMG+CRITICALPOWER[bu]}
//Esprit
	if (d.gI("ESP1C").checked==true && movemode==0.7)
		{var esprit=d.gI("ESP1").value;AddHPR=AddHPR+ESPRITHP[esprit];AddMPR=AddMPR+ESPRITMP[esprit]}
	if (d.gI("ESP2C").checked==true && movemode==0.7)
		{var esprit=d.gI("ESP2").value;AddHPR=AddHPR+ESPRITHP[esprit];AddMPR=AddMPR+ESPRITMP[esprit]}
//Fast HP Recovery
	if (d.gI("FHR1C").checked==true)
		{var fastcast1=d.gI("FHR1").value;AddHPR=AddHPR+(FASTHPRECOVERY[fastcast1]*movemode)}
	if (d.gI("FHR2C").checked==true)
		{var fastcast1=d.gI("FHR2").value;AddHPR=AddHPR+(FASTHPRECOVERY[fastcast1]*movemode)}
	if (d.gI("FHR3C").checked==true)
		{var fastcast1=d.gI("FHR3").value;AddHPR=AddHPR+(FASTHPRECOVERY[fastcast1]*movemode)}
	if (d.gI("FHR4C").checked==true)
		{var fastcast1=d.gI("FHR4").value;AddHPR=AddHPR+(FASTHPRECOVERY[fastcast1]*movemode)}
//Fast Mana Recovery
	if (d.gI("FMR1C").checked==true)
		{var fastcast1=d.gI("FMR1").value;AddMPR=AddMPR+(FASTMANARECOVERY[fastcast1]*movemode)}
	if (d.gI("FMR2C").checked==true)
		{var fastcast1=d.gI("FMR2").value;AddMPR=AddMPR+(FASTMANARECOVERY[fastcast1]*movemode)}
//Fast Spell Casting
	if (d.gI("FSC1C").checked==true)
		{var fastcast1=d.gI("FSC1").value;BuffCAST=BuffCAST*FASTCAST[fastcast1]}
	if (d.gI("FSC2C").checked==true)
		{var fastcast2=d.gI("FSC2").value;BuffCAST=BuffCAST*FASTCAST[fastcast2]}
//Final Fortress
	if (d.gI("FFOC").checked==true)
		{var finalfortress=d.gI("FFO").value;AddPDEF30=AddPDEF30+FINALFORTRESS[finalfortress]}
//Final Frenzy
	if (d.gI("FFRC").checked==true)
		{var finalfrenzy=d.gI("FFR").value;AddPATK30=AddPATK30+FINALFRENZY[finalfrenzy]}
//Focus Mind
	if (d.gI("FCM1C").checked==true)
		{var fastcast1=d.gI("FCM1").value;AddMPR=AddMPR+(FOCUSMIND[fastcast1]*movemode)}
	if (d.gI("FCM2C").checked==true)
		{var fastcast1=d.gI("FCM2").value;AddMPR=AddMPR+(FOCUSMIND[fastcast1]*movemode)}
//Knighthood
	if (d.gI("KNTC").checked==true)
		{AddPDEF=AddPDEF+86.9;BuffSHIELDPDEF=BuffSHIELDPDEF*1.1}
//Master of Combat
	if (d.gI("MOCC").checked==true && (WpnType=="Fist"||WpnMAS=="Sword"||WpnMAS=="Blunt"||WpnType=="Polearm"||WpnType=="Duals"))
		{AddPATK=AddPATK+80;BuffCP=BuffCP*1.05}
//Necromancy
	if (d.gI("NECC").checked==true)
		{BuffMATK=BuffMATK*1.03}
//Quick Step
	if (d.gI("QS1C").checked==true)
		{var quickstep1=d.gI("QS1").value;AddSPEED=AddSPEED+QUICKSTEP[quickstep1]}
	if (d.gI("QS2C").checked==true)
		{var quickstep2=d.gI("QS2").value;AddSPEED=AddSPEED+QUICKSTEP[quickstep2]}
//Quiver of Holding
	if (d.gI("QOHC").checked==true)
		{var quiverofholding=d.gI("QOH").value;weightlimit=weightlimit*QUIVEROFHOLDING[quiverofholding]}
//Seal of Strife
	if (d.gI("SOSC").checked==true)
		{var sealofstrife=d.gI("SOS").value;BuffCP=BuffCP*SEALOFSTRIFE[sealofstrife]}
//Shadow Sense
	if (d.gI("SHSC").checked==true)
		{BuffACC=BuffACC+3}
//Shield Mastery
	if (d.gI("SDM1C").checked==true)
		{var shieldmastery1=d.gI("SDM1").value;BuffShieldBlockRate=BuffShieldBlockRate*SHIELDMASTERY[shieldmastery1]}
	if (d.gI("SDM2C").checked==true)
		{var shieldmastery2=d.gI("SDM2").value;BuffShieldBlockRate=BuffShieldBlockRate*SHIELDMASTERY[shieldmastery2]}
//Summon Lore
	if (d.gI("SMLC").checked==true)
		{AddPDEF=AddPDEF+60;BuffCAST=BuffCAST*1.07}
//Vital Force
	if (d.gI("VTF1C").checked==true && movemode==1.5)
		{var fastcast1=d.gI("VTF1").value;AddHPR=AddHPR+VITALFORCEHP[fastcast1]*CONMOD;AddMPR=AddMPR+VITALFORCEMP[fastcast1]}
	if (d.gI("VTF2C").checked==true && movemode==1.5)
		{var fastcast1=d.gI("VTF2").value;AddHPR=AddHPR+VITALFORCEHP[fastcast1]*CONMOD;AddMPR=AddMPR+VITALFORCEMP[fastcast1]}
//Weight Limit
	if (d.gI("WTL1C").checked==true)
		{var weightlimit1=d.gI("WTL1").value;weightlimit=weightlimit*WEIGHTLIMIT[weightlimit1]}
	if (d.gI("WTL2C").checked==true)
		{var weightlimit2=d.gI("WTL2").value;weightlimit=weightlimit*WEIGHTLIMIT[weightlimit2]}
	if (d.gI("WTL3C").checked==true)
		{var weightlimit3=d.gI("WTL3").value;weightlimit=weightlimit*WEIGHTLIMIT[weightlimit3]}

//Base Stat Display
d.gI("STR").innerHTML=STR
d.gI("CON").innerHTML=CON
d.gI("DEX").innerHTML=DEX
d.gI("INT").innerHTML=INT
d.gI("WIT").innerHTML=WIT
d.gI("MEN").innerHTML=MEN

//HP calculation
var hp=BaseHP*CONMOD*BuffHP+NECKLACEHP+AddHP
if (hp<1)
	{hp=1}
var hp2=Math.floor(hp)
var hp60=hp*.6
if (hp60<1)
	{hp60=0}
var hp602=Math.floor(hp60)
var hp30=hp*.3
if (hp30<1)
	{hp30=0}
var hp302=Math.floor(hp30)
if (d.gI("HP1").checked==true)
{d.gI("HP").innerHTML=(hp2)}
else if (d.gI("HP6").checked==true && hp602<=0)
{d.gI("HP").innerHTML=(hp602+"/"+hp2);alert("You have died. In a stat calculator. You lose the Internets.")}
else if (d.gI("HP6").checked==true && hp602>=1)
{d.gI("HP").innerHTML=(hp602+"/"+hp2)}
else if (d.gI("HP3").checked==true && hp302<=0)
{d.gI("HP").innerHTML=(hp302+"/"+hp2);alert("You have died. In a stat calculator. You lose the Internets.")}
else if (d.gI("HP3").checked==true && hp302>=1)
{d.gI("HP").innerHTML=(hp302+"/"+hp2)}

//CP calculation
var cp=BaseCP*CONMOD*BuffCP+AddCP
var cp2=Math.floor(cp)
d.gI("CP").innerHTML=cp2

//MP calculation
var mp=BaseMP*MENMOD*BuffMP+AddMP
var mp2=Math.floor(mp)
d.gI("MP").innerHTML=mp2

//P.Atk. calculation
var WpnPATK=WpnPATK+(weaponunderenchant*underPATK)+(weaponoverenchant*overPATK)
if (d.gI("WMM1C").checked==true||d.gI("WMM2C").checked==true||d.gI("WMM3C").checked==true)
	{MasteryPATK=1.45}
else if (d.gI("WMFC").checked==true)
	{MasteryPATK=1.085}
var patk=WpnPATK*STRMOD*LVLMOD*NECKLACEPATK*MasteryPATK*BuffPATK+AddPATK
var patk2=Math.floor(patk)
var patk60=WpnPATK*STRMOD*LVLMOD*NECKLACEPATK*MasteryPATK*BuffPATK*BuffPATK60+AddPATK+AddPATK60
var patk602=Math.floor(patk60)
var patk30=WpnPATK*STRMOD*LVLMOD*NECKLACEPATK*MasteryPATK*BuffPATK*BuffPATK60*BuffPATK30+AddPATK+AddPATK60+AddPATK30
var patk302=Math.floor(patk30)
if (d.gI("HP1").checked==true)
{d.gI("PATK").innerHTML=patk2;combatpatk=patk}
else if (d.gI("HP6").checked==true)
{d.gI("PATK").innerHTML=patk602;combatpatk=patk60}
else if (d.gI("HP3").checked==true)
{d.gI("PATK").innerHTML=patk302;combatpatk=patk30}

//M.Atk. calculation
var WpnMATK=WpnMATK+(weaponunderenchant*underMATK)+(weaponoverenchant*overMATK)
if (d.gI("WMM1C").checked==true||d.gI("WMM2C").checked==true||d.gI("WMM3C").checked==true)
	{MasteryMATK=WpnMATK*(INTMOD*INTMOD)*(LVLMOD*LVLMOD)*0.17*BuffMATK}
var matk=WpnMATK*(INTMOD*INTMOD)*(LVLMOD*LVLMOD)*NECKLACEMATK*BuffMATK+AddMATK+MasteryMATK
var matk2=Math.floor(matk)
d.gI("MATK").innerHTML=matk2
var combatmatk=matk

//P.Def. calculation
var Cloak=4
apdef=Cloak+Helmet+Upper+Lower+Glove+Boot
pdef=apdef*LVLMOD*BuffPDEF+AddPDEF
pdef2=Math.floor(pdef)
pdef60=apdef*LVLMOD*BuffPDEF*BuffPDEF60+AddPDEF+AddPDEF60
pdef602=Math.floor(pdef60)
pdef30=apdef*LVLMOD*BuffPDEF*BuffPDEF60*BuffPDEF30+AddPDEF+AddPDEF60+AddPDEF30
pdef302=Math.floor(pdef30)
if (d.gI("HP1").checked==true)
{d.gI("PDEF").innerHTML=pdef2;shieldpdef=Shield*BuffSHIELDPDEF+pdef+AddSHIELDPDEF;combatpdef=pdef}
else if (d.gI("HP6").checked==true)
{d.gI("PDEF").innerHTML=pdef602;shieldpdef=Shield*BuffSHIELDPDEF+pdef60+AddSHIELDPDEF;combatpdef=pdef60}
else if (d.gI("HP3").checked==true)
{d.gI("PDEF").innerHTML=pdef302;shieldpdef=Shield*BuffSHIELDPDEF+pdef30+AddSHIELDPDEF;combatpdef=pdef30}

//Shield P.Def. calculation
shieldpdef2=Math.floor(shieldpdef)
d.gI("SHDPDEF").innerHTML=shieldpdef2

//M.Def. calculation
jmdef=Ring1+Ring2+Earring1+Earring2+Necklace
mdef=jmdef*LVLMOD*MENMOD*BuffMDEF+AddMDEF
mdef2=Math.floor(mdef)
d.gI("MDEF").innerHTML=mdef2
mdef60=jmdef*LVLMOD*MENMOD*BuffMDEF*BuffMDEF60+AddMDEF+AddMDEF60
mdef602=Math.floor(mdef60)
mdef30=jmdef*LVLMOD*MENMOD*BuffMDEF*BuffMDEF60*BuffMDEF30+AddMDEF+AddMDEF60+AddMDEF30
mdef302=Math.floor(mdef30)
if (d.gI("HP1").checked==true)
{d.gI("MDEF").innerHTML=mdef2;shieldmdef=Shield*BuffSHIELDPDEF+mdef+AddSHIELDPDEF;combatmdef=mdef}
else if (d.gI("HP6").checked==true)
{d.gI("MDEF").innerHTML=mdef602;shieldmdef=Shield*BuffSHIELDPDEF+mdef60+AddSHIELDPDEF;combatmdef=mdef60}
else if (d.gI("HP3").checked==true)
{d.gI("MDEF").innerHTML=mdef302;shieldmdef=Shield*BuffSHIELDPDEF+mdef30+AddSHIELDPDEF;combatmdef=mdef30}

//Shield M.Def. calculation
shieldmdef2=Math.floor(shieldmdef)
d.gI("SHDMDEF").innerHTML=shieldmdef2

//Accuracy Calculation
var Accuracy=(Math.sqrt(DEX)*6)+LVL+WpnAcc+RINGOFQUEENACC+RINGOFBAIUMACC+RINGOFCOREACC+BuffACC
var Accuracy2=Math.floor(Accuracy)
var Accuracy60=Accuracy2+BuffACC60
var Accuracy602=Math.floor(Accuracy60)
var Accuracy30=Accuracy2+BuffACC30+BuffACC60
var Accuracy302=Math.floor(Accuracy30)
if (d.gI("HP1").checked==true)
{d.gI("ACCURACY").innerHTML=Accuracy2;combataccuracy=Accuracy}
else if (d.gI("HP6").checked==true)
{d.gI("ACCURACY").innerHTML=Accuracy602;combataccuracy=Accuracy60}
else if (d.gI("HP3").checked==true)
{d.gI("ACCURACY").innerHTML=Accuracy302;combataccuracy=Accuracy30}

//Evasion Calculation
var Evasion=(Math.sqrt(DEX)*6)+LVL+ShieldEvasion+BuffEVA
var Evasion2=Math.floor(Evasion)
var Evasion60=Evasion2+BuffEVA60
var Evasion602=Math.floor(Evasion60)
var Evasion30=Evasion2+BuffEVA30+BuffEVA60
var Evasion302=Math.floor(Evasion30)
if (d.gI("HP1").checked==true)
{d.gI("EVASION").innerHTML=Evasion2;combatevasion=Evasion+combatevasion}
else if (d.gI("HP6").checked==true)
{d.gI("EVASION").innerHTML=Evasion602;combatevasion=Evasion60+combatevasion}
else if (d.gI("HP3").checked==true)
{d.gI("EVASION").innerHTML=Evasion302;combatevasion=Evasion30+combatevasion}

//Critical calculation
var finalcritical=basecritical+AddCRIT+subcritical
if (finalcritical > 500)
	{finalcritical=500}
var finalcritical2=Math.floor(finalcritical)
var finalcritical60=basecritical+AddCRIT+subcritical+AddCRIT60
if (finalcritical60 > 500)
	{finalcritical60=500}
var finalcritical602=Math.floor(finalcritical60)
var finalcritical30=basecritical+AddCRIT+subcritical+AddCRIT60+AddCRIT30
if (finalcritical30 > 500)
	{finalcritical30=500}
var finalcritical302=Math.floor(finalcritical30)
if (d.gI("HP1").checked==true)
{d.gI("CRITICAL").innerHTML=finalcritical2;combatcritical=finalcritical}
else if (d.gI("HP6").checked==true)
{d.gI("CRITICAL").innerHTML=finalcritical602;combatcritical=finalcritical60}
else if (d.gI("HP3").checked==true)
{d.gI("CRITICAL").innerHTML=finalcritical302;combatcritical=finalcritical30}

//Speed calculation
if (movemode==1)
{
var Speed=BaseWalk*DEXMOD*BuffSPEED+AddSPEED
var Speed2=Math.floor(Speed)
var Speed60=BaseWalk*DEXMOD*BuffSPEED*BuffSPEED60+AddSPEED+AddSPEED60
var Speed602=Math.floor(Speed60)
var Speed30=BaseWalk*DEXMOD*BuffSPEED*BuffSPEED60*BuffSPEED30+AddSPEED+AddSPEED60+AddSPEED30
var Speed302=Math.floor(Speed30)
}
else
{
var Speed=BaseRun*DEXMOD*BuffSPEED+AddSPEED
var Speed2=Math.floor(Speed)
var Speed60=BaseRun*DEXMOD*BuffSPEED*BuffSPEED60+AddSPEED+AddSPEED60
var Speed602=Math.floor(Speed60)
var Speed30=BaseRun*DEXMOD*BuffSPEED*BuffSPEED60*BuffSPEED30+AddSPEED+AddSPEED60+AddSPEED30
var Speed302=Math.floor(Speed30)
}
if (d.gI("HP1").checked==true)
	{d.gI("SPEED").innerHTML=Speed2;combatspeed=Speed}
else if (d.gI("HP6").checked==true)
	{d.gI("SPEED").innerHTML=Speed602;combatspeed=Speed60}
else if (d.gI("HP3").checked==true)
	{d.gI("SPEED").innerHTML=Speed302;combatspeed=Speed30}

//Atk. Spd. calculation
var atkspd=WpnSpd*DEXMOD*RINGOFBAIUMASPD*BuffASPD+AddASPD
var atkspd2=Math.floor(atkspd)
var atkspd60=WpnSpd*DEXMOD*RINGOFBAIUMASPD*BuffASPD*BuffASPD60+AddASPD
var atkspd602=Math.floor(atkspd60)
var atkspd30=WpnSpd*DEXMOD*RINGOFBAIUMASPD*BuffASPD*BuffASPD60*BuffASPD30+AddASPD
var atkspd302=Math.floor(atkspd30)
if (d.gI("HP1").checked==true)
	{d.gI("ATKSPD").innerHTML=atkspd2;combatatkspd=atkspd}
else if (d.gI("HP6").checked==true)
	{d.gI("ATKSPD").innerHTML=atkspd602;combatatkspd=atkspd60}
else if (d.gI("HP3").checked==true)
	{d.gI("ATKSPD").innerHTML=atkspd302;combatatkspd=atkspd30}

//Casting Spd. calculation
var castingspd=333*WITMOD*RINGOFBAIUMCAST*BuffCAST+AddCAST
var castingspd2=Math.floor(castingspd)
d.gI("CASTINGSPD").innerHTML=castingspd2

//Weight Limit
var weightlimit2=weightlimit*CONMOD
d.gI("WTL").innerHTML=Math.floor(weightlimit2)
var weight50=weightlimit2*0.5+weightpenalty
d.gI("WEIGHT50").innerHTML=Math.floor(weight50)

//HP Regen, CP Regen, MP Regen
var basehpregen
var basempregen
if (LVL>0 && LVL<11)
	{basehpregen=1.95+(LVL/20);basempregen=0.9;basecpregen=2}
if (LVL>10)
	{basehpregen=1.4+(LVL/10)}
if (LVL>10 && LVL<21)
	{basempregen=1.2;basecpregen=2.5}
if (LVL>20 && LVL<31)
	{basempregen=1.5;basecpregen=3.5}
if (LVL>30 && LVL<41)
	{basempregen=1.8;basecpregen=4.5}
if (LVL>40 && LVL<51)
	{basempregen=2.1;basecpregen=5.5}
if (LVL>50 && LVL<61)
	{basempregen=2.4;basecpregen=6.5}
if (LVL>60 && LVL<71)
	{basempregen=2.7;basecpregen=7.5}
if (LVL>70 && LVL<81)
	{basempregen=3;basecpregen=8.5}
var hpregen=(basehpregen*CONMOD*LVLMOD*BuffHPR*movemode+AddHPR)*BuffHPR2
var hpregen2=Math.floor(hpregen*10)/10
d.gI("HPREGEN").innerHTML=hpregen2
var cpregen=basecpregen*CONMOD*LVLMOD*BuffCPR*movemode+AddCPR
var cpregen2=Math.floor(cpregen*10)/10
d.gI("CPREGEN").innerHTML=cpregen2
var mpregen=(basempregen*MENMOD*LVLMOD*BuffMPR*movemode+AddMPR)*BuffMPR2+AddMPR2
var mpregen2=Math.floor(mpregen*10)/10
d.gI("MPREGEN").innerHTML=mpregen2

if (enemy==11)
	{d.gI("LVO").disabled=true
	var ELVL=LVL
	var ELVLMOD=LVLMOD
	var EDEXMOD=DEXMOD
	var targettype="player"
	var Eranddmg=randdmg
	var Eminranddmg=minranddmg
	var EWpnCrt=WpnCrt
	var Ebowdelay=bowdelay
	var Eweapongrade=weapongrade
	var Eweapon_sa=weapon_sa
	var EBuffCRITDMG=BuffCRITDMG*RINGOFBAIUMCRITDMG*RINGOFQUEENCRITDMG
	var EAddCRITDMG=AddCRITDMG
	var Ecombatpatk=combatpatk*2
	if (((Eweapongrade=="S"||Eweapongrade=="A"||Eweapongrade=="S80") && Eweapon_sa!="no SA")||Eweapongrade=="H")
		{Ecombatpatk=Ecombatpatk*1.05}
	var Ecombatpdef=combatpdef
	var EAccuracy=Accuracy
	var EEvasion=Evasion
	var Ecombatcritical=combatcritical
	var Ecombatatkspd=combatatkspd
	var EWpnMAS=WpnMAS
	if (EWpnMAS=="Fist"||EWpnMAS=="Duals")
		{var Ecombatpatk=Ecombatpatk/2
		var Ecombatatkspd=Ecombatatkspd*2}
//Enemy Shield stats
	var EShield=Shield
	var EBuffSHIELDPDEF=BuffSHIELDPDEF
	var EAddSHIELDPDEF=AddSHIELDPDEF
	var Eshieldpdef=EShield*EBuffSHIELDPDEF+Ecombatpdef+EAddSHIELDPDEF
	var Eblock360=block360
	var EBuffShieldBlockRate=BuffShieldBlockRate
	var EShieldBlockRate=BaseShieldBlockRate*EBuffShieldBlockRate*EDEXMOD
	if ((position==2||position==3) && block360==false)
		{EShieldBlockRate=0}
	if (WpnMAS=="Bow")
		{EShieldBlockRate=EShieldBlockRate*3}
	if (EShieldBlockRate>=1)
		{EShieldBlockRate=1}
	var Eavgshieldpdef=Eshieldpdef*EShieldBlockRate+Ecombatpdef*(1-EShieldBlockRate)
//Enemy Reflect Damage
	var Ebuffrd=(BuffRD+FRINTEZZARD+VALAKASRD)
	if (WpnType=="Bow")
		{Ebuffrd=0}
	d.gI("EBUFFS").disabled=true}

////////////////////Damage Calculations
//Shield Block Rate
var ShieldBlockRate=BaseShieldBlockRate*DEXMOD*BuffShieldBlockRate
if (EWpnType=="Bow"||EWpnType=="YumiBow")
	{ShieldBlockRate=ShieldBlockRate*3}
if ((position==4||position==5) && block360==false)
	{ShieldBlockRate=0}
if (ShieldBlockRate>=1)
	{ShieldBlockRate=1}
var shieldblockrate=Math.floor(ShieldBlockRate*100)+"%"
d.gI("SHDBLOCKRATE").innerHTML=shieldblockrate
var avgshieldpdef=shieldpdef*ShieldBlockRate+combatpdef*(1-ShieldBlockRate)
var avgshieldpdef2=Math.floor(avgshieldpdef)
d.gI("AVGSHIELDPDEF").innerHTML=avgshieldpdef2

if (d.gI("SS").checked==true)
	{combatpatk=combatpatk*2
	var sscost = + d.gI("SSC1").value}

if ((position==2||position==3) && Eblock360==false)
	{EShieldBlockRate=0}

if (((weapongrade=="S"||weapongrade=="A"||weapongrade=="S80") && weapon_sa!="no SA" && targettype=="player")||weapongrade=="H" && targettype=="player")
	{combatpatk=combatpatk*1.05}

//Base Damage
if (WpnType=="Fist"||WpnMAS=="Duals")
	{var combatpatk=combatpatk/2
	var combatatkspd=combatatkspd*2}
var basedmg=(combatpatk*70/Ecombatpdef)*BuffDAMAGE
var Ebasedmg=(Ecombatpatk*70/combatpdef)*EBuffDAMAGE

//Normal hit Min/Max Damage
if (position>=1 && position <=3)
	{var mindmg=(basedmg)*minranddmg
	var mindmg2=Math.floor(mindmg)
	if (mindmg2==0) {mindmg2=1}
	var maxdmg=(basedmg)*randdmg
	var maxdmg2=Math.floor(maxdmg)
	if (maxdmg2==0) {maxdmg2=1}
	d.gI("DMGRNG").innerHTML=(mindmg2+"-"+maxdmg2)}
else
	{var mindmg2=0;var maxdmg2=0;var mindmg=0;var maxdmg=0
	d.gI("DMGRNG").innerHTML=("-")}
if (position==1||position==4||position==5)
	{var Emindmg=(Ebasedmg)*minranddmg
	var Emindmg2=Math.floor(Emindmg)
	if (Emindmg2==0) {Emindmg2=1}
	var Emaxdmg=(Ebasedmg)*randdmg
	var Emaxdmg2=Math.floor(Emaxdmg)
	if (Emaxdmg2==0) {Emaxdmg2=1}
	d.gI("EDMGRNG").innerHTML=(Emindmg2+"-"+Emaxdmg2)}
else
	{var Emindmg2=0;var Emaxdmg2=0;var Emindmg=0;var Emaxdmg=0
	d.gI("EDMGRNG").innerHTML=("-")}

//Average Damage on Shield Blocks
if ((position==1 && EShieldBlockRate!=0)||((position==2||position==3) && Eblock360==true && EShieldBlockRate!=0))
	{var shieldblockdmg=(combatpatk*70/Eshieldpdef)*BuffDAMAGE
	var shieldblockdmg2=Math.floor(shieldblockdmg)
	if (shieldblockdmg2==0) {shieldblockdmg2=1}}
else
	{var shieldblockdmg2=0;var shieldblockdmg=0}
if ((position==1 && ShieldBlockRate!=0)||((position==4||position==5) && block360==true && ShieldBlockRate!=0))
	{var Eshieldblockdmg=(Ecombatpatk*70/shieldpdef)*EBuffDAMAGE
	var Eshieldblockdmg2=Math.floor(Eshieldblockdmg)
	if (Eshieldblockdmg2==0) {Eshieldblockdmg2=1}}
else
	{var Eshieldblockdmg2=0;var Eshieldblockdmg=0}

//Shield Block Min/Max Damage
if ((position==1 && EShieldBlockRate!=0)||((position==2||position==3) && Eblock360==true && EShieldBlockRate!=0))
	{var shieldmindmg=(shieldblockdmg)*minranddmg
	var shieldmindmg2=Math.floor(shieldmindmg)
	if (shieldmindmg2==0) {shieldmindmg2=1}
	var shieldmaxdmg=(shieldblockdmg)*randdmg
	var shieldmaxdmg2=Math.floor(shieldmaxdmg)
	if (shieldmaxdmg2==0) {shieldmaxdmg2=1}
	d.gI("SHDDMGRNG").innerHTML=(shieldmindmg2+"-"+shieldmaxdmg2)}
else
	{var shieldmindmg2=0;var shieldmaxdmg2=0;var shieldmindmg=0;var shieldmaxdmg=0
	d.gI("SHDDMGRNG").innerHTML=("-")}


if ((position==1 && ShieldBlockRate!=0)||((position==4||position==5) && block360==true && ShieldBlockRate!=0))
	{var Eshieldmindmg=(Eshieldblockdmg)*minranddmg
	var Eshieldmindmg2=Math.floor(Eshieldmindmg)
	if (Eshieldmindmg2==0) {Eshieldmindmg2=1}
	var Eshieldmaxdmg=(Eshieldblockdmg)*randdmg
	var Eshieldmaxdmg2=Math.floor(Eshieldmaxdmg)
	if (Eshieldmaxdmg2==0) {Eshieldmaxdmg2=1}
	d.gI("ESHIELDDMGRNG").innerHTML=(Eshieldmindmg2+"-"+Eshieldmaxdmg2)}
else
	{var Eshieldmindmg2=0;var Eshieldmaxdmg2=0;var Eshieldmindmg=0;var Eshieldmaxdmg=0
	d.gI("ESHIELDDMGRNG").innerHTML=("-")}

//Critical hits
if (position==1)
	{var critchance=(combatcritical/10)*BuffCOMBATCRIT+AddCOMBATCRIT
	if (critchance>=100)
		{critchance=100}
	var critchance2=Math.floor(critchance*10)/10}
else if (position==2)
	{var critchance=(combatcritical/10)*1.2*BuffCOMBATCRIT+AddCOMBATCRIT
	if (critchance>=100)
		{critchance=100}
	var critchance2=Math.floor(critchance*10)/10}
else if (position==3)
	{var critchance=(combatcritical/10)*1.4*BuffCOMBATCRIT+AddCOMBATCRIT
	if (critchance>=100)
		{critchance=100}
	var critchance2=Math.floor(critchance*10)/10}
else
	{var critchance2=0;var critchance=0}
	d.gI("CRITCHANCE").innerHTML=(critchance2+"%")
if (position==1)
	{var Ecritchance=Ecombatcritical/10
	if (Ecritchance>=100)
		{Ecritchance=100}
	var Ecritchance2=Math.floor(Ecritchance*10)/10}
else if (position==4)
	{var Ecritchance=(Ecombatcritical/10)*1.2
	if (Ecritchance>=100)
		{Ecritchance=100}
	var Ecritchance2=Math.floor(Ecritchance*10)/10}
else if (position==5)
	{var Ecritchance=(Ecombatcritical/10)*1.4
	if (Ecritchance>=100)
		{Ecritchance=100}
	var Ecritchance2=Math.floor(Ecritchance*10)/10}
else
	{var Ecritchance2=0;var Ecritchance=0}
	d.gI("ECRITCHANCE").innerHTML=(Ecritchance2+"%")

var critdmg=((basedmg/BuffDAMAGE)*2*BuffCRITDMG*RINGOFBAIUMCRITDMG*RINGOFQUEENCRITDMG+(AddCRITDMG*70/Ecombatpdef))*BuffDAMAGE
var sbcritdmg=((shieldblockdmg/BuffDAMAGE)*2*BuffCRITDMG*RINGOFBAIUMCRITDMG*RINGOFQUEENCRITDMG+(AddCRITDMG*70/Eshieldpdef))*BuffDAMAGE
if (position>=1 && position <=3)
	{var mincritdmg=critdmg*minranddmg
	var mincritdmg2=Math.floor(mincritdmg)
	if (mincritdmg2==0) {mincritdmg2=1}
	var maxcritdmg=critdmg*randdmg
	var maxcritdmg2=Math.floor(maxcritdmg)
	if (maxcritdmg2==0) {maxcritdmg2=1}
	d.gI("CRITDMG").innerHTML=(mincritdmg2+"-"+maxcritdmg2)}
else
	{var mincritdmg=0;var mincritdmg2=0;var maxcritdmg=0;var maxcritdmg2=0;
	d.gI("CRITDMG").innerHTML=("-")}
if ((position==1 && EShieldBlockRate!=0)||((position==2||position==3) && Eblock360==true && EShieldBlockRate!=0))
	{var minsbcritdmg=sbcritdmg*minranddmg
	var minsbcritdmg2=Math.floor(minsbcritdmg)
	if (minsbcritdmg2==0) {minsbcritdmg2=1}
	var maxsbcritdmg=sbcritdmg*randdmg
	var maxsbcritdmg2=Math.floor(maxsbcritdmg)
	if (maxsbcritdmg2==0) {maxsbcritdmg2=1}
	d.gI("SBCRITDMG").innerHTML=(minsbcritdmg2+"-"+maxsbcritdmg2)}
else
	{var minsbcritdmg=0;var minsbcritdmg2=0;var maxsbcritdmg=0;var maxsbcritdmg2=0
	d.gI("SBCRITDMG").innerHTML=("-")}
if (position==1||position==4||position==5)
	{var Ecritdmg=Ebasedmg*2*EBuffCRITDMG+(EAddCRITDMG*70/combatpdef)
	var Emincritdmg=Ecritdmg*minranddmg
	var Emincritdmg2=Math.floor(Emincritdmg)
	if (Emincritdmg2==0) {Emincritdmg2=1}
	var Emaxcritdmg=Ecritdmg*randdmg
	var Emaxcritdmg2=Math.floor(Emaxcritdmg)
	if (Emaxcritdmg2==0) {Emaxcritdmg2=1}
	d.gI("ECRITDMG").innerHTML=(Emincritdmg2+"-"+Emaxcritdmg2)}
else
	{var Ecritdmg=0;var Emincritdmg=0;var Emincritdmg2=0;var Emaxcritdmg=0;var Emaxcritdmg2=0
	d.gI("ECRITDMG").innerHTML=("-")}
if ((position==1 && ShieldBlockRate!=0)||((position==4||position==5) && block360==true && ShieldBlockRate!=0))
	{var Esbcritdmg=Eshieldblockdmg*2*EBuffCRITDMG+(EAddCRITDMG*70/shieldpdef)
	var Eminsbcritdmg=Esbcritdmg*minranddmg
	var Eminsbcritdmg2=Math.floor(Eminsbcritdmg)
	if (Eminsbcritdmg2==0) {Eminsbcritdmg2=1}
	var Emaxsbcritdmg=Esbcritdmg*randdmg
	var Emaxsbcritdmg2=Math.floor(Emaxsbcritdmg)
	if (Emaxsbcritdmg2==0) {Emaxsbcritdmg2=1}
	d.gI("ESBCRITDMG").innerHTML=(Eminsbcritdmg2+"-"+Emaxsbcritdmg2)}
else
	{var Esbcritdmg=0;var Eminsbcritdmg=0;var Eminsbcritdmg2=0;var Emaxsbcritdmg=0;var Emaxsbcritdmg2=0
	d.gI("ESBCRITDMG").innerHTML=("-")}

//Hits per second
if (position>=1 && position <=3)
	{var hitspersecond=combatatkspd/500
		if (WpnType=="Bow")
			{hitspersecond=(combatatkspd/1000)*(1500/bowdelay)}
		if (WpnType=="YumiBow")
			{hitspersecond=(combatatkspd/1000)*(1500/(bowdelay*1.4166))}
	var hitspersecond2=Math.floor(hitspersecond*100)/100
	d.gI("HITSPERSECOND").innerHTML=(hitspersecond2)}
else
	{var hitspersecond=0;var hitspersecond2=0;
	d.gI("HITSPERSECOND").innerHTML=("-")}
if ((position==1||position==4||position==5))
	{var Ehitspersecond=Ecombatatkspd/500
		if (EWpnType=="Bow")
			{Ehitspersecond=(Ecombatatkspd/1000)*(1500/Ebowdelay)}
		if (EWpnType=="YumiBow")
			{Ehitspersecond=(Ecombatatkspd/1000)*(1500/(Ebowdelay*1.4166))}
	var Ehitspersecond2=Math.floor(Ehitspersecond*100)/100
	d.gI("EHITSPERSECOND").innerHTML=(Ehitspersecond2)}
else
	{var Ehitspersecond=0;var Ehitspersecond2=0;
	d.gI("EHITSPERSECOND").innerHTML=("-")}

//Chance to hit
var hitratio=combataccuracy-EEvasion
if (hitratio<-24.5)
	{hitratio=0.275}
else if (hitratio<-20 && hitratio>=-24.5)
	{hitratio=0.25+((hitratio+25)*5/100)}
else if (hitratio<-15 && hitratio>=-20)
	{hitratio=0.50+((hitratio+20)*3/100)}
else if (hitratio<-10 && hitratio>=-15)
	{hitratio=0.65+((hitratio+15)*2/100)}
else if (hitratio<-0 && hitratio>=-10)
	{hitratio=0.75+((hitratio+10)*1.5/100)}
else if (hitratio<5 && hitratio>=0)
	{hitratio=0.905+(hitratio*1/100)}
else if (hitratio<10 && hitratio>=5)
	{hitratio=0.955+((hitratio-5)*0.5/100)}
else if (hitratio>10)
	{hitratio=0.98}
var Ehitratio=EAccuracy-combatevasion
if (Ehitratio<-24.5)
	{Ehitratio=0.275}
else if (Ehitratio<-20 && Ehitratio>=-24.5)
	{Ehitratio=0.25+((Ehitratio+25)*5/100)}
else if (Ehitratio<-15 && Ehitratio>=-20)
	{Ehitratio=0.50+((Ehitratio+20)*3/100)}
else if (Ehitratio<-10 && Ehitratio>=-15)
	{Ehitratio=0.65+((Ehitratio+15)*2/100)}
else if (Ehitratio<-0 && Ehitratio>=-10)
	{Ehitratio=0.75+((Ehitratio+10)*1.5/100)}
else if (Ehitratio<5 && Ehitratio>=0)
	{Ehitratio=0.905+(Ehitratio*1/100)}
else if (Ehitratio<10 && Ehitratio>=5)
	{Ehitratio=0.955+((Ehitratio-5)*0.5/100)}
else if (Ehitratio>10)
	{Ehitratio=0.98}
if (position==2)
	{hitratio=hitratio*1.1}
if (position==3)
	{hitratio=hitratio*1.2}
if (position==4)
	{Ehitratio=Ehitratio*1.1}
if (position==5)
	{Ehitratio=Ehitratio*1.2}
if (hitratio>0.98) {hitratio=0.98}
if (Ehitratio>0.98) {Ehitratio=0.98}

var hitratio2=Math.floor(hitratio*100)+"%"
if (position>=1 && position<=3)
	{d.gI("HITRATIO").innerHTML=(hitratio2)}
else
	{d.gI("HITRATIO").innerHTML=("-")}
var Ehitratio2=Math.floor(Ehitratio*100)+"%"
if (position==1||position==4||position==5)
	{d.gI("EHITRATIO").innerHTML=(Ehitratio2)}
else
	{d.gI("EHITRATIO").innerHTML=("-")}

//Damage Per Second
if (position>=1 && position<=3)
	{var avgdmg=(basedmg*(1-EShieldBlockRate)*(1-(critchance/100))+shieldblockdmg*EShieldBlockRate*(1-(critchance/100))+critdmg*(1-EShieldBlockRate)*(critchance/100)+sbcritdmg*EShieldBlockRate*(critchance/100))*hitspersecond*hitratio}
else
	{var avgdmg=0}
if ((position==1||position==4||position==5))
	{var Eavgdmg=(Ebasedmg*(1-ShieldBlockRate)*(1-(Ecritchance/100))+Eshieldblockdmg*ShieldBlockRate*(1-(Ecritchance/100))+Ecritdmg*(1-ShieldBlockRate)*(Ecritchance/100)+Esbcritdmg*ShieldBlockRate*(Ecritchance/100))*Ehitspersecond*Ehitratio}
else
	{var Eavgdmg=0}

//Soulshot Efficiency
var weaponss=WEAPON[weapon][3]
if (WpnType=="Fist"||WpnMAS=="Duals")
	{var sseff=sscost*weaponss*(hitspersecond/2)}
else
	{var sseff=sscost*weaponss*hitspersecond}
d.gI("SSC3").innerHTML=Math.floor(sseff)
if (WpnType=="Fist"||WpnMAS=="Duals")
	{var sseff2=avgdmg/hitratio/sseff;
	var ssmiss=(hitratio-hitratio*hitratio)/2+1;
	sseff2=(sseff2/ssmiss)}
else
	{var sseff2=avgdmg/hitratio/sseff}
if (sseff2>20000)
	{d.gI("SSC5").innerHTML="-"}
else
	{d.gI("SSC5").innerHTML=Math.floor(sseff2*100)}

//Vampiric Rage
if (WpnMAS!="Bow")
	{var vr=avgdmg*(BuffVR+EARRINGOFANTHARASVR+EARRINGOFZAKENVR)
	var vr2=Math.floor(vr*10)/10}
else
	{var vr2=0}
d.gI("VR").innerHTML=vr2

//Reflect Damage
var buffrd=(BuffRD+FRINTEZZARD+VALAKASRD)
if (EWpnType=="Bow"||EWpnType=="YumiBow")
	{buffrd=0}
var buffrd2=Math.floor(100*buffrd)
d.gI("RD").innerHTML=(buffrd2+"%")

var avgdmg2=(Eavgdmg*buffrd)+avgdmg
var avgdmg3=Math.floor(avgdmg2*10)/10
d.gI("AVGDMG").innerHTML=avgdmg3
var Eavgdmg2=(avgdmg*Ebuffrd)+Eavgdmg
var Eavgdmg3=Math.floor(Eavgdmg2*10)/10
d.gI("EAVGDMG").innerHTML=Eavgdmg3

//Resurrection
if (JOB=="BI")
	{var resur=d.gI("RES2").value}
else if (JOB=="EE")
	{var resur=d.gI("RES3").value}
else
	{var resur=d.gI("RES4").value}
var baseres=RESURRECTION[resur]
var rescap=RESURRECTION[resur]+0.2
var resu=baseres*WITMOD
if (resu>rescap) {resu=rescap}
d.gI("RES11").innerHTML=Math.round(resu*100)+"%"
var mresur=d.gI("RES7").value
var basemres=RESURRECTION[mresur]
var mrescap=RESURRECTION[mresur]+0.2
var mresu=basemres*WITMOD
if (mresu>mrescap) {mresu=mrescap}
d.gI("RES12").innerHTML=Math.round(mresu*100)+"%"

//Heal Calc
var HTY=+d.gI("HLT").value
var HTYPE=(HEALTYPEARRAY[HTY])
var HSI=+d.gI(HTYPE).value
var HPWR=0
var HSLVL=0
	if (HTYPE=="BATTLEHEAL") {HPWR=BATTLEHEAL[HSI];HSLVL=BATTLEHEALSLVL[HSI]}
	else if (HTYPE=="DIVINEHEAL") {HPWR=DIVINEHEAL[HSI];HSLVL=DIVINEHEALSLVL[HSI]}
	else if (HTYPE=="ELEMENTALHEAL") {HPWR=ELEMENTALHEAL[HSI];HSLVL=ELEMENTALHEALSLVL[HSI]}
	else if (HTYPE=="GBATTLEHEAL") {HPWR=GBATTLEHEAL[HSI];HSLVL=GBATTLEHEALSLVL[HSI]}
	else if (HTYPE=="GGROUPHEAL") {HPWR=GGROUPHEAL[HSI];HSLVL=GGROUPHEALSLVL[HSI]}
	else if (HTYPE=="GHEAL") {HPWR=GHEAL[HSI];HSLVL=GHEALSLVL[HSI]}
	else if (HTYPE=="GROUPHEAL") {HPWR=GROUPHEAL[HSI];HSLVL=GROUPHEALSLVL[HSI]}
	else if (HTYPE=="HEAL") {HPWR=HEAL[HSI];HSLVL=HEALSLVL[HSI]}
	else if (HTYPE=="HOLYBLESSING") {HPWR=HOLYBLESSING[HSI];HSLVL=HOLYBLESSINGSLVL[HSI]}
	else if (HTYPE=="MHEAL") {HPWR=MHEAL[HSI];HSLVL=MHEALSLVL[HSI]}
	else if (HTYPE=="SACRIFICE") {HPWR=SACRIFICE[HSI];HSLVL=SACRIFICESLVL[HSI]}
	else if (HTYPE=="SELFHEAL") {HPWR=SELFHEAL[HSI];HSLVL=SELFHEALSLVL[HSI]}
	else if (HTYPE=="VITALIZE") {HPWR=VITALIZE[HSI];HSLVL=VITALIZESLVL[HSI]}
	else if (HTYPE=="MGROUPHEAL") {HPWR=MGROUPHEAL[HSI];HSLVL=MGROUPHEALSLVL[HSI]}
var HMATKREQ=MATKREQHEALS[HSLVL]
var matktest=HMATKREQ-combatmatk
var spseffect=0
	if (d.gI("SPS").checked==true)
		{combatmatk=combatmatk*2
		spseffect=SPSEFFECT[HSLVL]*0.41
		if (matktest>0)
			{spseffect=spseffect-(matktest*0.48)
				if (spseffect<0) {spseffect=0}}
		d.gI("SPS").disabled=false;
		d.gI("BSPS").disabled=true;}
	else if (d.gI("BSPS").checked==true)
		{combatmatk=combatmatk*4
		spseffect=SPSEFFECT[HSLVL]
		if (matktest>0)
			{spseffect=spseffect-(matktest*0.48)
				if (spseffect<0) {spseffect=0}}
		d.gI("BSPS").disabled=false;
		d.gI("SPS").disabled=true;}
	else
		{d.gI("SPS").disabled=false;
		d.gI("BSPS").disabled=false;}
//Prayer
var hprayer=+d.gI("HPRAYER").value
var prayer=PRAYER[hprayer]
BuffHEAL=BuffHEAL*prayer
	if (d.gI("HANTHARAS").checked==true)
		{BuffHEAL=BuffHEAL*1.1}
	if (d.gI("HORFEN").checked==true)
		{BuffHEAL=BuffHEAL*1.06}
	if (d.gI("HZAKEN").checked==true)
		{BuffHEAL=BuffHEAL*1.1}
	if (d.gI("HDRKC").checked==true)
		{BuffHEAL=BuffHEAL*1.04}
	if (d.gI("HLIFE").checked==true)
		{BuffHEAL=BuffHEAL*1.3}
	if (d.gI("HANGEL").checked==true)
		{BuffHEAL=BuffHEAL*0.2}
	if (d.gI("HZEALOT").checked==true)
		{BuffHEAL=BuffHEAL*0.5}
	if (d.gI("HCURSE").checked==true)
		{BuffHEAL=BuffHEAL*0.5}
	if (d.gI("HDEMON").checked==true)
		{BuffHEAL=BuffHEAL*0.5}
	if (d.gI("HDEATH").checked==true)
		{BuffHEAL=BuffHEAL*0.7}
	if (d.gI("HZERK").checked==true)
		{BuffHEAL=BuffHEAL*2}
var healpower=((Math.sqrt(combatmatk))+HPWR+spseffect)*BuffHEAL
var healpower2=Math.floor(healpower)
d.gI("HEALAMOUNT").innerHTML=(healpower2)

//d.gI("TEST").innerHTML=(healpower2)
//alert("ELVL="+TARGET[enemy][1]+";targettype="+TARGET[enemy][2]+";EDEX="+TARGET[enemy][3]+";EDEXMOD="+TARGET[enemy][4]+";ESTRMOD="+TARGET[enemy][5]+";Eranddmg="+TARGET[enemy][6]+";EWpnType="+TARGET[enemy][7]+";EWpnAcc="+TARGET[enemy][8]+";EWpnCrt="+TARGET[enemy][9]+";EWpnSpd="+TARGET[enemy][10]+";Eweapongrade="+TARGET[enemy][11]+";Eweapon_sa="+TARGET[enemy][12]+";EWpnPATK="+TARGET[enemy][13]+";EMasteryPATK="+TARGET[enemy][14]+";EBuffPATK="+TARGET[enemy][15]+";EAddPATK="+TARGET[enemy][16])
//alert(";Eapdef="+TARGET[enemy][17]+";EBuffPDEF="+TARGET[enemy][18]+";EAddPDEF="+TARGET[enemy][19]+";EBuffACC="+TARGET[enemy][20]+";EBuffEVA="+TARGET[enemy][21]+";EAddCRIT="+TARGET[enemy][22]+";Esubcritical="+TARGET[enemy][23]+";EBuffASPD="+TARGET[enemy][24]+";EAddASPD="+TARGET[enemy][25]+";EBuffCRITDMG="+TARGET[enemy][26]+";EAddCRITDMG="+TARGET[enemy][27]+";EShield="+TARGET[enemy][28]+";EBuffSHIELDPDEF="+TARGET[enemy][29]+";EAddSHIELDPDEF="+TARGET[enemy][30]+";Eblock360="+TARGET[enemy][31]+";EBuffShieldBlockRate="+TARGET[enemy][32]+";Ebuffrd="+TARGET[enemy][33])
}