// --- 1. Import necessary modules ---
const express = require('express');
const http = require('http');
const { Server } = require('socket.io');

// --- 2. Initialize the application ---
const app = express();
const server = http.createServer(app);
const io = new Server(server); // Attach Socket.IO to our HTTP server

// --- 3. Serve the Client UI ---
// When someone visits our site, send them the HTML file
app.get('/', (req, res) => {
    res.sendFile(__dirname + '/index.html');
});

// --- 4. The Real-Time Communication Logic ---
// This block runs every time a new user connects to the server
io.on('connection', (socket) => {
    console.log('A human connected to the chat.');

    // Listen for a specific event called 'chat message' from this user
    socket.on('chat message', (msg) => {
        console.log('Message received: ' + msg);
        
        // Broadcast that exact message to ALL connected users (including the sender)
        io.emit('chat message', msg);
    });

    // Listen for the user closing their browser tab
    socket.on('disconnect', () => {
        console.log('A human disconnected.');
    });
});

// --- 5. Start listening ---
const PORT = 3000;
server.listen(PORT, () => {
    console.log(`Server is awake and listening on http://localhost:${PORT}`);
});
