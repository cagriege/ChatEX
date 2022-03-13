# ChatEX
# Description
This is a showcase implementation of a chat server and client using Java. The server component is standalone and multithreaded. Threads are obtained from a thread pool and used for managing client connections. Clients are implemented as simple Swing applications.

Clients communicate with the server using Sockets. The server component caches Socket output streams making it easier to publish messages to clients.
