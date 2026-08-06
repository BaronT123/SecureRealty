import 'dart:convert';

import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

class DocumentScreen extends StatefulWidget {
  final String jwtToken;

  const DocumentScreen({
    super.key,
    required this.jwtToken,
  });

  @override
  State<DocumentScreen> createState() => _DocumentScreenState();
}

class _DocumentScreenState extends State<DocumentScreen> {
  bool uploading = false;
  String conversationId = "";
  List<dynamic> documents = [];
  Future<void> uploadDocument() async {
    FilePickerResult? result = await FilePicker.pickFiles(
      type: FileType.custom,
      allowedExtensions: ['pdf'],
      withData: true,
    );

    if (result == null) return;

    final pickedFile = result.files.single;

    setState(() {
      uploading = true;
    });

    try {
      var request = http.MultipartRequest(
        "POST",
        Uri.parse("http://localhost:8080/documents/upload"),
      );

      request.headers["Authorization"] =
          "Bearer ${widget.jwtToken}";

      request.fields["conversationId"] =
          conversationId;

      request.files.add(
  http.MultipartFile.fromBytes(
    "file",
    pickedFile.bytes!,
    filename: pickedFile.name,
  ),
);

      var response = await request.send();

      final body = await response.stream.bytesToString();

      if (response.statusCode == 200) {

        await fetchDocuments();
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text("Document uploaded successfully!"),
          ),
        );
      } else {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text(body),
          ),
        );
      }
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text(e.toString()),
        ),
      );
    }

    setState(() {
      uploading = false;
    });
  }
  Future<void> fetchConversation() async {

  final response = await http.get(
    Uri.parse("http://localhost:8080/conversations"),
    headers: {
      "Authorization": "Bearer ${widget.jwtToken}",
    },
  );

  if (response.statusCode == 200) {

    List<dynamic> conversations = jsonDecode(response.body);

    conversationId = conversations[0]["id"];

    await fetchDocuments();

    setState(() {});
  }
}
Future<void> fetchDocuments() async {

  if (conversationId.isEmpty) return;

  final response = await http.get(
    Uri.parse(
      "http://localhost:8080/documents/$conversationId",
    ),
    headers: {
      "Authorization": "Bearer ${widget.jwtToken}",
    },
  );

  if (response.statusCode == 200) {

    setState(() {

      documents = jsonDecode(response.body);

    });

  } else {

    debugPrint(response.body);

  }
}
@override
void initState() {
  super.initState();
  fetchConversation();
}
  @override
  Widget build(BuildContext context) {
    return Column(
  children: [

    Padding(
      padding: const EdgeInsets.all(16),
      child: SizedBox(
        width: double.infinity,
        child: ElevatedButton.icon(
          onPressed: uploading ? null : uploadDocument,
          icon: const Icon(Icons.upload_file),
          label: const Text("Upload PDF"),
        ),
      ),
    ),

    Expanded(
      child: documents.isEmpty
          ? const Center(
              child: Text("No documents uploaded yet."),
            )
          : ListView.builder(

              itemCount: documents.length,

              itemBuilder: (context, index) {

                final doc = documents[index];

                return Card(

                  margin: const EdgeInsets.symmetric(
                    horizontal: 16,
                    vertical: 8,
                  ),

                  child: ListTile(

                    leading: const Icon(
                      Icons.picture_as_pdf,
                      color: Colors.red,
                    ),

                    title: Text(doc["fileName"]),

                    subtitle: Text(
                      "Uploaded by ${doc["uploadedBy"]}",
                    ),

                    trailing: const Icon(Icons.download),

                    onTap: () {

                      // Download next

                    },

                  ),

                );

              },

            ),
    ),

  ],
);
  }
}