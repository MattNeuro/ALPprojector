# ALPprojector
Control interface to mark regions of interest in a microscope image and activate those regions on a Vialux 7000 digital micromirror device

This interface requires two components: the ProjectorService application (in C++) controls the DMD, and the ProjectorClient (in JAVA) provides the GUI and creates and submits the image masks to the ProjectorService. 

The old MatlabProjectorClient used an older version of the ProjectorService to interface with, and due to changes in the protocol, is no longer compatible with the current version of the service. 
