import 'package:flutter/material.dart';
import 'package:stomp_dart_client/stomp_dart_client.dart';

import 'package:http/http.dart' as http;
import '../models/chat_message.dart';
import 'dart:convert';

class ChatScreen extends StatefulWidget {

  final String jwtToken;
  final String currentUser;

  final String conversationId;
  final String receiver;

  const ChatScreen({
    super.key,
    required this.jwtToken,
    required this.currentUser,
    required this.conversationId,
    required this.receiver,
  });

  @override
  State<ChatScreen> createState() => _ChatScreenState();
}

class _ChatScreenState extends State<ChatScreen> {

  // Controls whatever is typed into the TextField
  final TextEditingController messageController = TextEditingController();
  final ScrollController scrollController = ScrollController();
  late StompClient stompClient;
  // Stores all chat messages
  List<ChatMessage> messages = [];


  
  
Future<void> loadMessages() async {

  try {

    final response = await http.get(
      Uri.parse(
        "http://localhost:8080/messages/${widget.conversationId}",
      ),
      headers: {
        "Content-Type": "application/json",
        "Authorization": "Bearer ${widget.jwtToken}",
      },
    );

    print("===== LOAD MESSAGES =====");
    print("Status Code: ${response.statusCode}");
    print("Body: ${response.body}");

    if (response.statusCode == 200) {

      if (response.body.isEmpty) {
        print("Messages response is EMPTY!");
        return;
      }

      List<dynamic> jsonData = jsonDecode(response.body);

      setState(() {

        messages = jsonData
            .map((json) => ChatMessage.fromJson(json))
            .toList();

      });

      scrollToBottom();

    } else {

      print("Failed to load messages");
      print("Status: ${response.statusCode}");
      print("Body: ${response.body}");

    }

  } catch (e, stackTrace) {

    print("Exception while loading messages:");
    print(e);
    print(stackTrace);

  }

}
void scrollToBottom() {

  WidgetsBinding.instance.addPostFrameCallback((_) {

    if (scrollController.hasClients) {

      scrollController.animateTo(
        scrollController.position.maxScrollExtent,
        duration: const Duration(milliseconds: 300),
        curve: Curves.easeOut,
      );

    }

  });

}
void subscribeToConversation() {

  stompClient.subscribe(

    destination: "/topic/chat/${widget.conversationId}",

    callback: (frame) {

      if (frame.body != null) {

        final chat =
            ChatMessage.fromJson(
              jsonDecode(frame.body!)
            );

        setState(() {
          messages.add(chat);
        });
        scrollToBottom();

      }

    },

  );

}
void connectWebSocket() {

  stompClient = StompClient(
  config: StompConfig.sockJS(
    url: 'http://localhost:8080/chat',

    stompConnectHeaders: {
      "Authorization": "Bearer ${widget.jwtToken}",
    },

    webSocketConnectHeaders: {
      "Authorization": "Bearer ${widget.jwtToken}",
    },

    onConnect: (frame) {
      print("Connected to WebSocket");
    },
  ),
);

  stompClient.activate();

}
  void sendMessage() {
    

    String text = messageController.text;

    // Prevent sending empty messages
    if (text.trim().isEmpty) {
      return;
    }

    final message = {
    "conversationId": widget.conversationId,
    "sender": widget.currentUser,
    "receiver": widget.receiver,
    "message": text,
  };

  // Send through WebSocket
  stompClient.send(
    destination: "/app/sendMessage",
    body: jsonEncode(message),
  );
    // Clear the text box
    messageController.clear();
    loadMessages();
  }

  @override
void initState() {
  super.initState();
  connectWebSocket();
  loadMessages();
  
}

  @override
  Widget build(BuildContext context) {
    
    return Scaffold(

      appBar: AppBar(
        title: Text(widget.receiver),
      ),

      body: Column(

        children: [

          Expanded(

            child: ListView.builder(
              controller: scrollController,
              itemCount: messages.length,

              itemBuilder: (context, index) {
                final chat = messages[index];
                bool isMe = chat.sender == widget.currentUser;

                return Padding(

                  padding: const EdgeInsets.all(8.0),

                  child: Align(

                    alignment: isMe
                    ? Alignment.centerRight
                    : Alignment.centerLeft,

                    child: Container(

                      padding: const EdgeInsets.all(12),

                      decoration: BoxDecoration(
                        color: isMe
                        ? Colors.blue
                        : Colors.grey.shade300,
                        borderRadius: BorderRadius.circular(12),
                      ),

                      child: Text(
                        chat.message,
                        style: TextStyle(
                        color: isMe ? Colors.white : Colors.black,
                      ),
                      ),
                    ),
                  ),
                );
              },
            ),
          ),

          const Divider(height: 1),

          Padding(

            padding: const EdgeInsets.all(8),

            child: Row(

              children: [

                Expanded(

                  child: TextField(

                    controller: messageController,

                    decoration: const InputDecoration(
                      hintText: "Type a message...",
                      border: OutlineInputBorder(),
                    ),

                    onSubmitted: (value) {
                      sendMessage();
                    },
                  ),
                ),

                const SizedBox(width: 8),

                ElevatedButton(

                  onPressed: sendMessage,

                  child: const Text("Send"),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}