# KUKAiiwa_bridge_sunrise
Repository for KUKA bridge communication

An attempt to simplify the data extraction of the KUKA iiwa robot by bridging the communication to an OPC-UA server.

Approach:

The installation of OPC-UA for java http://opcfoundation.github.io/UA-Java-Legacy/ inside the KUKA iiwa controller can be tedious and to some extent harmful to the controller.

While not the most optimal method, the approach opted for this solution involves collecting data from the controller (using KUKA libraries) and transporting it to a ModBus server that is running locally in the controller.

A bridge is then created to receive the ModBus information. The information is then passed onto the OPC-UA framework. This bridge will be running as an application in the Windows controller.

External users will then, be able to access and perform data exchange from OPC-UA clients.
