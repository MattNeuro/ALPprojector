function uploadMask(event, source)
    global mask;
    global client;

    % Connect to DMD projector service
    if (isempty(client))
        client = tcpclient("localhost", 8041);
    end

    % Write mask over the TCP client to the DMD service
    bitMask = uint8(mask');
    bitMask = bitMask(:); % Convert to one-dimensional vector for upload.
    write(client, bitMask, 'uint8');
end

