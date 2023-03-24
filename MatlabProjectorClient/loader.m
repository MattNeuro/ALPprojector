disp('Starting load');
global mouseLoc;
global imagePanel;
global pos;
global gui;
global maskSpots;
global colorMask;
global mask;
global spotSize;
global client;

% Initialize global variables.
maskSpots   = [];
mouseLoc    = [0, 0];
spotSize    = 10;       % default to 10 pixels diameter

% Take screen capture. Adjust to capture the live image.
robot   = java.awt.Robot();
pos     = [0 0 400 300]; % [left top width height]
rect    = java.awt.Rectangle(pos(1),pos(2),pos(3),pos(4));


gui = uifigure('Name',          'DMD controller',       ...
                'Position',     [200 200 915 610]);
imagePanel   = uiimage(gui,                              ...
                'Position',     [110, 5, 800, 600]);
uploadButton = uibutton(gui,                             ...
                'Position',         [5 5 100 50],       ...
                'Text',             'Upload',           ...
                'ButtonPushedFcn',   @uploadMask);
clearButton  = uibutton(gui,                            ...
                'Position',         [5 110 100 50],     ...
                'Text',             'Clear ROIs',       ...
                'ButtonPushedFcn',   @clearMaskSpots);
sizeDownButton = uibutton(gui,                          ...
                'Position',         [5 210 45 50],      ...
                'Text',             '-',                ...
                'ButtonPushedFcn',   @decreaseSpotSize);
sizeUpButton = uibutton(gui,                            ...
                'Position',         [55 210 45 50],      ...
                'Text',             '+',                ...
                'ButtonPushedFcn',   @increaseSpotSize);

set (gui, 'WindowButtonMotionFcn',  @mouseMove); % Mouse moved, update tracker
set (gui, 'WindowButtonDownFcn',    @addToMask); % Mouse clicked, add spot to mask
set (gui, 'WindowKeyPress',         @checkKeypress); % Detect key-presses.

updateMouseLocation();
drawMask();

while true
    cap = robot.createScreenCapture(rect);
    % Convert to an RGB image
    rgb = typecast(cap.getRGB(0,0,cap.getWidth,cap.getHeight,[],0,cap.getWidth),'uint8');
    imgData = zeros(cap.getHeight,cap.getWidth,3,'uint8');
    imgData(:,:,1) = reshape(rgb(3:4:end),cap.getWidth,[])';
    imgData(:,:,2) = reshape(rgb(2:4:end),cap.getWidth,[])';
    imgData(:,:,3) = reshape(rgb(1:4:end),cap.getWidth,[])';
    
    % Resize mask to fit live-video display
    resizedMask = imresize(colorMask, [size(imgData,1), size(imgData,2)]);
    
    % Determine scaling factor for cursor
    circleSize = spotSize * (size(resizedMask,1) / size(colorMask,1));

    % Combine live-video with mask overlay
    fusion = imfuse(imgData, resizedMask, "blend");
    
    % Draw mouse cursor on top:
    fusion = insertShape(fusion, "circle", [mouseLoc(1), mouseLoc(2), circleSize]);
    imagePanel.ImageSource = fusion;

    % Pause so we don't bind up all CPU resources to run this script. 
    pause(0.1);     
end


%%  Check for hot-keys to increase / decrease spot size
function checkKeypress (object, event)
    switch (event.Key)
        case 'g'
            decreaseSpotSize (object, event);
        case 'h'
            increaseSpotSize (object, event);
    end
end

%%  Increase or decrease the spot-size.
%
%   This will also increase or decrease the size of the spot in our image
%   mask. Size changes should be reflected at the next refresh.
function increaseSpotSize (object, event)
    global spotSize;
    spotSize = spotSize * 1.2;
end
function decreaseSpotSize (object, event)
    global spotSize;
    spotSize = spotSize / 1.2;
end

%%  Clear the entire mask
%   
%   Empties the list of ROIs on the mask, then redraws the mask.
function clearMaskSpots (object, event)
    global maskSpots;
    maskSpots = [];
    drawMask();
end

%%  Add current mouse location to mask
%
%   Spot size is determined by the spotSize variable. Always draw a circle,
%   this should suffice for the time being.
function addToMask (object, event)
    global mouseLoc;
    global maskSpots;
    global pos;
    global spotSize;
    sizeX = 1024;       % Size of our DMD. Hard-code this for simplicity. Sorry.
    sizeY = 768;        %

    updateMouseLocation();
    index = size(maskSpots,1);

    % Resize spots to compensate for scaling of mask relative to image.
    spotX = mouseLoc(1) * (sizeX / pos(3));
    spotY = mouseLoc(2) * (sizeY / pos(4));

    % Add location to mask list, but ONLY if we clicked inside the image.
    if mouseLoc(1) >= 0 && mouseLoc(2) >= 0
        maskSpots((index + 1), :) = [spotX spotY spotSize]
    end

    drawMask();
end

%%  Generate a new mask image
function drawMask ()
    global mask;
    global maskSpots;
    global colorMask;
    sizeX = 1024;       % Size of our DMD. Hard-code this for simplicity. Sorry.
    sizeY = 768;        %    

    % Always add an empty spot to create a blank mask
    mask = createMask(drawcircle('Center', [-10, -10], 'Radius', 0), sizeY, sizeX);

    % Then add a circle on the mask for each spot we clicked
    for i=1:size(maskSpots,1)
        roi = drawcircle('Center', [maskSpots(i,1), maskSpots(i,2)],'Radius',maskSpots(i,3));
        mask = mask | createMask(roi,sizeY,sizeX); % add next ROI to existing mask
    end
    colorMask = cast(mask,"single");
    colorMask = repmat(colorMask,[1,1,3]);
end


function mouseMove (object, event)
    updateMouseLocation();
end

function updateMouseLocation()
    global mouseLoc;
    global imagePanel;
    global pos;
    global gui;
    rawLoc = get (gui, 'CurrentPoint');
   
    %   Convert raw mouse location on the GUI into mouse coordinates in the
    %   figure
    x = rawLoc(1);
    y = rawLoc(2);

    panelLoc   = imagePanel.Position;
    parentLoc  = imagePanel.Parent.Position;
    
    x = x - panelLoc(1);
    y = parentLoc(4) - panelLoc(2) - y;
    x = (pos(3) / panelLoc(3)) * x;
    y = (pos(4) / panelLoc(4)) * y;

    mouseLoc = [x,y];
end
