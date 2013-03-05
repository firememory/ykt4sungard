/* --------------------------------------------
 * ��������: F930103.c
 * ��������: 2005-09-05
 * ��������: �Ž�
 * �汾��Ϣ: 1.0.0.0
 * ������:�´���ʱ���豸����
 * --------------------------------------------
 * �޸�����:
 * �޸���Ա:
 * �޸�����:
 * �汾��Ϣ:
 * ��ע��Ϣ:
 * --------------------------------------------*/
#define _IN_SQC_
#include <string.h>
#include <stdio.h>
#include "pubfunc.h"
#include "pubdb.h"
#include "pubdef.h"
#include "errdef.h"
#include "dbfunc.h"
#include "fdsqc.h"

int F930103(TRUSERID *handle,int iRequest,ST_PACK *in_pack,int *pRetCode,char *szMsg)
{
	int ret = 0;
       T_t_tif_meslist tMesList;

	memset(&tMesList,0,sizeof(tMesList));

	des2src(tMesList.devphy999_id, in_pack->sdate0);
	trim(tMesList.devphy999_id);
	if(strlen(tMesList.devphy999_id)==0)
	{
		*pRetCode=E_DEVPHYID_NULL;
		goto L_RETU;
	}
	AddXmlItemStr(tMesList.incontent,XML_KEY_DEV999_ID,tMesList.devphy999_id);
	AddXmlItemInt(tMesList.incontent,XML_KEY_WORKMODE, in_pack->lvol3);
	AddXmlItemInt(tMesList.incontent,XML_KEY_LOCK1_TIME, in_pack->lvol5);
	AddXmlItemInt(tMesList.incontent,XML_KEY_LOCK2_TIME, in_pack->lvol6);
	AddXmlItemStr(tMesList.incontent,XML_KEY_IS_ALLEYWAY, in_pack->sstatus0);
	AddXmlItemStr(tMesList.incontent,XML_KEY_IS_SAVE, in_pack->sstatus1);
	AddXmlItemStr(tMesList.incontent,XML_KEY_IS_LCD, in_pack->sstatus2);
	AddXmlItemStr(tMesList.incontent,XML_KEY_SYSPWD, in_pack->semp_pwd);
	AddXmlItemInt(tMesList.incontent,XML_KEY_BAUDRATE, 0);
	AddXmlItemInt(tMesList.incontent,XML_KEY_CARDSTR, 100);
	tMesList.funid = iRequest;
	tMesList.level =1;
	ret=AddMsgLst(&tMesList);
	if(ret)
	{
		*pRetCode=ret;
		writelog(LOG_ERR,"AddMsgLst err[%d]",ret);
		goto L_RETU;
	}
	return 0;
L_RETU:
	return  -1;
}