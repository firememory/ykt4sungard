include ./mkstand.mak
LIBDIR=$(HOME)/ykt/lib
TARGET=libpubdb.a

SOURCE=\
esqlc\
acctransdtl\
bankcard\
bankcardtmp\
depositoper\
depositoperpos\
depositshoppos\
dictionary\
shoppos\
shoppostmp\
machinecode\
opersite\
cardver\
cardinstead\
transtype\
cfgfee\
cfgtrans\
seqnoctl\
branch\
card\
carddtl\
cardtransdtl\
cardtype\
custtype\
posdtltmp\
cfgver\
cfgsplit\
dept\
device\
devicetmp\
moduleauth\
account\
rptactive\
rptbanktransfer\
rptcardbalance\
rpterr\
rptoper\
rptrichdebt\
rptshpbalance\
rpttranscomp\
shop\
spefee\
subbalance\
subupd\
subsystem\
syskey\
savdtl\
tradelog\
warrant\
phydevice\
authcard\
customer\
custtmp\
coshis\
subject\
cuttypefee\
tradeserial\
rcvdtl\
histrlog\
histrserial\
v1rptcar\
v2rpterr\
vrpttot\
vrptoper\
vshpdptfee\
vrptactive\
cutinfoupd\
cardlist\
txdtl\
operdtl\
operright\
doorholiday\
site\
subsidy\
subsidytmp\
syspara\
feetype\
subslog\
sbparams\
writefailed\
device_collect_log\
shoptree\
cardbitmap\
cardbook\
cutrelation\
cutrltntmp\
grpcashrpt\
opgroups\
mealtype\
shopmeal\
rptdevm\
vrptdevm\
grpcashrpt\
operator\
drtimsec\
doorweek\
drhldtim\
doorgrp\
groupmem\
drtimgrp\
drgrprht\
drdevwk\
drdevtmsc\
drdevhld\
drdevtg\
drevent\
subdept\
subdept_v\
area\
thirdpartsys\
specialty\
cfgrakeoff\
trdcdshp\
trdcode\
devmntr\
attend\
rptfile\
purse\
scheduledtasklog\
scheduledtask\
blacksht\
actdaysum\
meslist\
bankcomp\
cadlosinf\
difftransfer
#vrpttolg
# =====================================================
all:$(TARGET)
	@echo $(TARGET) is up to date

clean:
	@./.clean.sh
	@rm -f  $(PUBIND)/dbincl/*stru.h
	@rm -f *.o $(TARGET) *stru.h  *.c *.bnd tp* *.lis

install:$(TARGET)
	@cp *_stru.h $(PUBIND)/dbincl
	@cp esqlc.h $(PUBIND)
	@./.gendb.sh
	@cp $(TARGET) $(LIBDIR)
	@echo $(TARGET) is moved to $(LIBDIR)

$(TARGET):$(SOURCE:=.o)
	$(AR) $(ARFLAGS) $@ $(SOURCE:=.o)
