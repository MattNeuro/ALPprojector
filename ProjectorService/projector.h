#pragma once

#include "projectorServer.h"

#include <windows.h>
#include <stdio.h>
#include <string>
#include <alpbasic.h>
#include <fstream>

// Clean-up
int				CleanUp(ALPB_HDEVICE* alpid, void* image, const char* msg = NULL, long nResult = -1);

// Determine resolution
bool			getResolutionByDMD(ALPB_DMDTYPES* nDmdType, long* nSizeX, long* nSizeY);

