# MULTITHREADED-CHAT-APPLICATION

"COMPANY": CODTECH IT SOLUTIONS

"NAME": HINAL ANIL MISTRY

"INTERN ID": CT04DK953

"DOMAIN": JAVA PROGRAMMING

"DURATION": 4 WEEKS

"MENTOR": NEELA SANTHOSH

## DESCRIPTION ##

Task 3 : Multithreaded Chat Application 

This task is performed using the ‘Eclipse IDE for Java Developers - 2025-03’.

The Multi-Client chat application follows a client-server design using Java's multithreading and sockets. The Client, the Server, and the ClientHandler make up it three basic components. Together, these provide concurrent real-time connection for many consumers using a central server.

## Server class : ##

The Server class is in charge of managing incoming client connections and maintaining the central chat system alive. Using a ServerSocket(ss) object, it listens for links on a certain port, usually 1234. Once a connection is authorized, the server generates a new instance of the ClientHandler class for that socket, hence ensuring separate administration for every client. Through a new thread assigned this event, multithreading enables the server to handle several clients simultaneously.

The server's basic Swing GUI interface includes a JTextArea (ta) to present real-time event log of events, like those of new customers joining. This enhances usability and gives the server administrator visual input. Until manually stopped or closed using its closeSS() method, the server keeps running.

## ClientHandler Class :  ##

The ClientHandler Class uses the Runnable interface to let threading enable simultaneous execution by controlling communication between the server and a specific client. It reads the username provided by the client after initialization by using a socket from the Server class and creating input and output streams (BufferedReader(br) and BufferedWriter(bw). This username is saved and used for recognition all through the meeting.

All instances of ClientHandler reflecting all now connected clients are kept in a shared static ArrayList. The ClientHandler uses this shared list to read a message from one client then rebroadcast it to all others by looping through it. Calling the SendMessage() function stops the sender's communication from being echoed to themselves. Moreover, the course provides methods for gently removing clients and closing resources should a user disconnect or an exception occurs by calling the removeClient() method.

## Client Class : ##

Every client communicates using input and output streams and uses a Socket to connect to the server. Upon connecting, the Client transmits its username to the server, which is then passed to all existing clients to denote the new participant. 
Using Java Swing and socket programming, the Client class creates a graphical user interface (GUI) for a user to engage in a group chat. Client Class real-time shows incoming messages from the server. The UI comprises a central text area i.e. chatArea (JTextPane) to display messages, a header with control icons (such back, call, video call, more) and Clientname i.e uname, and a footer with a send button and a message input area (JTextField). Once the user clicks send after entering a message, the message is sent across a socket by BufferedWriter(bw) and displayed on the chat pane in a custom style (right-aligned, colored). Incoming communications from other users are received on a different thread and shown in a different color.
The GUI design is arranged with a soothing color palette and evident visual hierarchy, therefore enhancing both user experience and appearance.

Together, the Server, ClientHandler, and Client classes present a simple but effective framework for a multi-client chat service. Although each ClientHandler instance manages one connection and passes messages to others, the server manages multiple clients by means of threads. The client interface allows for real-time interaction, therefore this configuration illustrates basic network communication and concurrent programming in Java.

## OUTPUT ##

Client :

![Image](https://github.com/user-attachments/assets/690591fc-cb63-4447-a665-363b9d5efb4f)

![Image](https://github.com/user-attachments/assets/ee6713a4-c134-475c-acbe-8075a0dde418)

![Image](https://github.com/user-attachments/assets/b4234fc0-e5f3-40c9-8924-622caea4610b)

Server :

![Image](https://github.com/user-attachments/assets/f4699479-5392-4911-ac3d-4a7f20ab40c5)
