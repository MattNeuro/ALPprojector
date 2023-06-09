#include "projectorServer.h"
#include "projector.h"

// Create a socket server to listen for incoming requests
bool createServer (ALPB_HDEVICE alpid, long nSizeX, long nSizeY) {
    WSADATA wsaData;
    int iResult;

    SOCKET ListenSocket = INVALID_SOCKET;
    SOCKET ClientSocket = INVALID_SOCKET;

    struct addrinfo* result = NULL;
    struct addrinfo hints;

    // Initialize Winsock
    iResult = WSAStartup(MAKEWORD(2, 2), &wsaData);
    if (iResult != 0) {
        printf("WSAStartup failed with error: %d\n", iResult);
        return 1;
    }

    ZeroMemory(&hints, sizeof(hints));
    hints.ai_family = AF_INET;
    hints.ai_socktype = SOCK_STREAM;
    hints.ai_protocol = IPPROTO_TCP;
    hints.ai_flags = AI_PASSIVE;

    // Resolve the server address and port
    iResult = getaddrinfo(NULL, DEFAULT_PORT, &hints, &result);
    if (iResult != 0) {
        printf("getaddrinfo failed with error: %d\n", iResult);
        WSACleanup();
        return 1;
    }

    // Create a SOCKET for the server to listen for client connections.
    ListenSocket = socket(result->ai_family, result->ai_socktype, result->ai_protocol);
    if (ListenSocket == INVALID_SOCKET) {
        printf("socket failed with error: %ld\n", WSAGetLastError());
        freeaddrinfo(result);
        WSACleanup();
        return 1;
    }

    // Setup the TCP listening socket
    iResult = bind(ListenSocket, result->ai_addr, (int)result->ai_addrlen);
    if (iResult == SOCKET_ERROR) {
        printf("bind failed with error: %d\n", WSAGetLastError());
        freeaddrinfo(result);
        closesocket(ListenSocket);
        WSACleanup();
        return 1;
    }

    freeaddrinfo(result);

    iResult = listen(ListenSocket, SOMAXCONN);
    if (iResult == SOCKET_ERROR) {
        printf("listen failed with error: %d\n", WSAGetLastError());
        closesocket(ListenSocket);
        WSACleanup();
        return 1;
    }

    // Accept a client socket
    ClientSocket = accept(ListenSocket, NULL, NULL);
    if (ClientSocket == INVALID_SOCKET) {
        printf("accept failed with error: %d\n", WSAGetLastError());
        closesocket(ListenSocket);
        WSACleanup();
        return 1;
    }

    // No longer need server socket
    closesocket(ListenSocket);

    // Allocate memory for the image mask, load the mask from file
    int imageSize           = nSizeX * nSizeY;              // set receive buffer length to size of image.
    unsigned char * image   = (unsigned char *) malloc(nSizeX * nSizeY);
    char recvBuf[1];        // Read one character at a time. Bit inefficient, but soit.
    int i                   = 0;

    // Receive until the peer shuts down the connection
    do {
        iResult = recv(ClientSocket, recvBuf, 1, 0);
        if (iResult > 0) {
            unsigned char cur = 0;
            cur = cur | recvBuf[0];

            FillMemory(
                image + (i * 8),	// row start address
                8,				    // row size in bits
                cur);			    // CHAR_BIT bits of image data

            i++;

            // Once we've received a full image:
            if (i >= (imageSize / 8)) {
                printf("Ready to display an image");

                // Upload the image to the DMD
                long ret = AlpbDevLoadRows(alpid, image, 0, nSizeY - 1);
                if (0 > ret) return CleanUp(&alpid, image, "Error: AlpbDevLoadRows (1)\n", ret);

                // Reset DMD mirrors, this actually projects the image
                ret = AlpbDevReset(alpid, ALPB_RESET_GLOBAL, 0);
                if (0 > ret) return CleanUp(&alpid, image, "Error: AlpbDevReset (1)\n", ret);

                // Reset counter for next image.
                i = 0;
            }
        }
        else if (iResult == 0)
            printf("Connection closing...\n");
        else {
            printf("recv failed with error: %d\n", WSAGetLastError());
            closesocket(ClientSocket);
            WSACleanup();
            return 1;
        }

    } while (iResult > 0);

    // shutdown the connection since we're done
    iResult = shutdown(ClientSocket, SD_SEND);
    if (iResult == SOCKET_ERROR) {
        printf("shutdown failed with error: %d\n", WSAGetLastError());
        closesocket(ClientSocket);
        WSACleanup();
        return 1;
    }

    // cleanup
    closesocket(ClientSocket);
    WSACleanup();
    return 0;
}