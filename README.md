# Sawgrass Boombox Demo Android App
A demo Android app (POC) for controlling a 12 channel multi-zone audio amplifier (B&K Components CT600) over local network (Telnet) via an **[RS-232 Port Server](https://static1.squarespace.com/static/54d27fb4e4b024eccdd9e569/t/555d23e0e4b00b0c736fc091/1432167392238/NPC22T.pdf)** (netCommander NPC-2201). A very simple and reliable way to control multi-zone audio on your smartphone without the need for cloud-connected devices. 

The app opens a socket connection to the specific IP & port of the RS-232 Port Server and sends simple commands (Telnet style). The Port Server forwards commands over RS-232 to the multi-zone amplifier. The protocol used by the amplifier is propriety and unpublished (as far as I can tell) but is simple enough to be easily reverse-engineered. Audio can be cast from the phone or online streaming service using a Chromecast Audio connected to the amplifier.

![image](https://user-images.githubusercontent.com/15943629/219526313-c9344139-3692-4abb-92b7-0f95d894bb99.png)

This demo app just turns on/off individual zones but could be expanded for volume control, input selection, equalization, power on/off, etc. The IP and port of the Port Server is hardcoded but could probably make use of automatic discovery protocols instead. The advantage of using an analog multi-zone amplifier compared to multiple smarthome speaker devices is low latency and perfect synchronization.   

The goal of this project is to very simply and cheaply add smartphone control to an existing multi-zone audio system without the need to replace perfectly good and working equipement that predates modern smarthome devices.
