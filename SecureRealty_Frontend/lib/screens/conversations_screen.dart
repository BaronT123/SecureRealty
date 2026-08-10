import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

import 'chat_screen.dart';

class ConversationsScreen extends StatefulWidget {
  final String jwtToken;
  final String currentUser;

  const ConversationsScreen({
    super.key,
    required this.jwtToken,
    required this.currentUser,
  });

  @override
  State<ConversationsScreen> createState() =>
      _ConversationsScreenState();
}

class _ConversationsScreenState
    extends State<ConversationsScreen> {

  List conversations = [];

  @override
  void initState() {
    super.initState();
    fetchConversations();
  }

  Future<void> fetchConversations() async {

    final response = await http.get(
      Uri.parse("http://localhost:8080/conversations"),
      headers: {
        "Authorization":
            "Bearer ${widget.jwtToken}",
      },
    );

    if (response.statusCode == 200) {

      setState(() {

        conversations =
            jsonDecode(response.body);

      });

    } else {

      print(response.body);

    }
  }

  @override
  Widget build(BuildContext context) {

    return ListView.builder(

      itemCount: conversations.length,

      itemBuilder: (context, index) {

        final conversation =
            conversations[index];

        String otherUser =
            conversation["customerId"] ==
                    widget.currentUser
                ? conversation["realtorId"]
                : conversation["customerId"];

        return ListTile(

          leading: const CircleAvatar(
            child: Icon(Icons.person),
          ),

          title: Text(otherUser),

          subtitle: const Text(
              "Tap to open conversation"),

          trailing: const Icon(
              Icons.arrow_forward_ios),

          onTap: () {

            Navigator.push(

              context,

              MaterialPageRoute(

                builder: (_) => ChatScreen(

                  jwtToken: widget.jwtToken,

                  currentUser: widget.currentUser,

                  conversationId: conversation["id"],

                  receiver: otherUser,

              )

              ),

            );

          },

        );
      },
    );
  }
}