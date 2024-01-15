# ALPprojector
Control interface to mark regions of interest in a microscope image, and activate those regions on a Vialux 7000 digital micromirror device

This interface requires two components: the ProjectorService application (in C++) controls the DMD, and the ProjectorClient (in JAVA) provides the GUI and creates and submits the image masks to the ProjectorService.

The old MatlabProjectorClient used an older version of the ProjectorService to interface with, and due to changes in the protocol, is no longer compatible with the current version of the service. 

![ALPprojector client, highlighting the basic functionality](https://github.com/MattNeuro/ALPprojector/blob/main/client_screenshot_1.png)


## Installation
In its default configuration, both components should be installed on the same computer; however, the GUI connects to the projector service over a network interface and can be run off a separate computer when needed. To do so, adjust the host variable in the projectorclient/NetworkClient class.

Windows binaries for the client and service are provided and require no further installation. After downloading the project, these can be executed as follows:
### ProjectorService
Navigate to `ALPprojector\ProjectorService\x64\Release` and start ProjectorService.exe. This will only succesfully run if the Vialux 7000 DMD is connected and installed correctly. It is strongly recommended to install the Vialux software and ensure proper operation of the DMD before running this.

If you prefer to not run binaries from the internet, or use a different architecture, open the included Visual Studio 2022 project (for legacy reasons, this is called Matlabprojector) and compile it as desired. Make sure the Vialux alpV42basic.lib library for your architecture is available.


### ALPClient
The ALPclient is written in JAVA, and requires the JAVA runtime environment. Please see [this page](https://www.java.com/en/download/help/download_options.html]) for instructions on installing JAVA on your platform. Once JAVA has been installed, navigate to `ALPprojector\ProjectorClient\target\` and run ProjectorClient-1.jar with the following command: `java -jar ProjectorClient-1.jar`. Depending on your installation, you may also be able to simply double-click the jar file to run it. 


## Using the ALPclient
The ALPclient is designed to work with any microscope software. It captures an area of the screen where your image is displayed and updates this in real-time. The capture area must be manually configured, but this configuration is stored for subsequent uses. The four parameters specify the location of your microscope image on your screen, and its width and height. The easiest way to calibrate this, is to use the `grid lines` option which will project a basic grid onto your sample. Adjust the offsets until the outside boundaries are just included in the preview, then change the width and height to match the width and height of your projection. 

### Mask Size
Clicking on the preview image will project a circular mask on that spot. The `mask size` slider controls the size of that spot. At its smallest, the user can turn on individual pixels, scaling up to what should hopefully be a large enough spot to mask off even the biggest of cells. When using the ProjectorClient, press `G` or `H` on your keyboard to decrease or increase the size of the mask. 

### Clear all
Clears all masks. This does not immediately upload the updated mask to the projector.

### Toggle all
Turn the entire DMD on or off. This may be useful to temporarily turn off all projected light, especially prior to generating an image mask.

### Deinterlace
Only activates every other pixel. This effectively reduces the amount of light hitting your sample, which should be useful to prevent bleaching while focusing or finding an area of interest.

### Grid Lines
Project a simple grid onto your sample; useful for calibrating the projected area and fine-tuning the optical properties of your system.

### Upload mask
Once you are satisfied with the image mask, use `upload mask` to actually send the new mask to the DMD and project it onto your sample.

