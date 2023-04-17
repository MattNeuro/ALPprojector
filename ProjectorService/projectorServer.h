#pragma once

#undef UNICODE

#define WIN32_LEAN_AND_MEAN // this seems unnecessary.

#include <winsock2.h>
#include <ws2tcpip.h>
#include <stdlib.h>
#include <stdio.h>
#include <alpbasic.h>

// Need to link with Ws2_32.lib
#pragma comment (lib, "Ws2_32.lib")
// #pragma comment (lib, "Mswsock.lib")

#define DEFAULT_BUFLEN	512
#define DEFAULT_PORT	"8041"

bool createServer(ALPB_HDEVICE alpid, long nSizeX, long nSizeY);