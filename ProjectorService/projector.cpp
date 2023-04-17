#include "projector.h"


int main(int argc, char* argv[])
{
	long			ret;
	ALPB_HDEVICE	alpid;
	ALPB_DMDTYPES	nDmdType;
	long			nSizeX, nSizeY;

	// The first thing to do is to allocate one ALP device.
	// The alpID serves for further requests to identify the device.
	ret = AlpbDevAlloc(0, &alpid);
	if (0 > ret) return CleanUp(NULL, NULL, "Error: AlpbDevAlloc\n", ret);

	// Detect DMD type
	ret = AlpbDevInquire(alpid, ALPB_DEV_DMDTYPE, &nDmdType);
	if (0 > ret) return CleanUp(NULL, NULL, "Error: AlpbDevInquire (DMD type)\n", ret);

	// Use DMD type to get the expected image resolution
	if (!getResolutionByDMD(&nDmdType,& nSizeX, &nSizeY))
		return CleanUp(NULL, NULL, "Error: Could not determine image size from DMD type.\n", ret);

	// Create TCP server to listen to incoming images
	createServer(alpid, nSizeX, nSizeY);

	// Clean-up, release device.
	printf("Clean up, release ALP device.\n");
	CleanUp(&alpid, NULL, "Properly finished\n");

	return 0;
}


// Evaluate DMD type
// Applications often depend on a particular DMD type. In this case just
// inquire ALPB_DEV_DMDTYPE and reject all unsupported types.
bool getResolutionByDMD(ALPB_DMDTYPES * nDmdType, long * sizeX, long * sizeY) {

	switch (*nDmdType) {
	case ALPB_DMDTYPE_DISCONNECT:
		printf("DMD type: DMD disconnected or not recognized\nEmulate 1080p\n");
		*sizeX = 1920; *sizeY = 1080;
		break;
	case ALPB_DMDTYPE_1080P_095A:
		printf("DMD type: 1080p .95\" Type-A\n");
		*sizeX = 1920; *sizeY = 1080;
		break;
	case ALPB_DMDTYPE_WUXGA_096A:
		printf("DMD type: WUXGA .96\" Type-A\n");
		*sizeX = 1920; *sizeY = 1200;
		break;
	case ALPB_DMDTYPE_XGA:
		printf("DMD type: XGA\n");
		*sizeX = 1024; *sizeY = 768;
		break;
	case ALPB_DMDTYPE_XGA_055A:
		printf("DMD type: XGA .55\" Type-A\n");
		*sizeX = 1024; *sizeY = 768;
		break;
	case ALPB_DMDTYPE_XGA_055X:
		printf("DMD type: XGA .55\" Type-X\n");
		*sizeX = 1024; *sizeY = 768;
		break;
	case ALPB_DMDTYPE_XGA_07A:
		printf("DMD type: XGA .7\" Type-A\n");
		*sizeX = 1024; *sizeY = 768;
		break;

	default:
		return CleanUp(NULL, NULL,
			"DMD type: (unknown)\n"
			"Error: DMD type not known\n",
			-1);
	}
	return true;
}



//		Perform clean-up.
//  This clears the memory and frees up our link to the DMD. Not stritly necessary since Windows should
//	do this for us when the application finishes, but good practice.
int CleanUp(ALPB_HDEVICE* alpid, void* image, const char* msg, long nResult)
{
	if (NULL != alpid) {
		long bHalt = 1;
		AlpbDevControl(*alpid, ALPB_DEV_HALT, &bHalt);	// actually only necessary in multithreading use
		AlpbDevFree(*alpid);	// close device driver
	}

	if (NULL != image)
		free(image);

	if (-1 != nResult) {
		char strMsg[100];
		long nSize = sizeof(strMsg);
		if (0 > AlpbDllGetResultText(nResult, &nSize, strMsg))
			printf("ALP basic API error code 0x%08x, see alpbasic.h\n", nResult);
		else
			printf("ALP basic API error (code 0x%08x, see alpbasic.h):\n %s\n", nResult, strMsg);
	}

	if (NULL != msg)
		printf(msg);
	return 1;
}