class ChatMessage {
  final String sender;
  final String receiver;
  final String message;

  ChatMessage({
    required this.sender,
    required this.receiver,
    required this.message,
  });

  factory ChatMessage.fromJson(Map<String, dynamic> json) {
    return ChatMessage(
      sender: json["sender"],
      receiver: json["receiver"],
      message: json["message"],
    );
  }
}