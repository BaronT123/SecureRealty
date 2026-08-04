import 'package:flutter/material.dart';

import 'package:http/http.dart' as http;
import 'screens/login_screen.dart';
class CustomerHomePage extends StatelessWidget {
  final String jwtToken;

  const CustomerHomePage({
    super.key,
    required this.jwtToken,
  });

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Customer Dashboard"),
        centerTitle: true,
      ),
      body: Center(
        child: SizedBox(
          width: 350,
          child: Card(
            elevation: 5,
            child: Padding(
              padding: const EdgeInsets.all(24),
              child: Column(
                mainAxisSize: MainAxisSize.min,
                children: [

                  const Icon(
                    Icons.home_work,
                    size: 70,
                    color: Colors.blue,
                  ),

                  const SizedBox(height: 20),

                  const Text(
                    "Welcome!",
                    style: TextStyle(
                      fontSize: 28,
                      fontWeight: FontWeight.bold,
                    ),
                  ),

                  const SizedBox(height: 10),

                  const Text(
                    "You're currently logged in as a Customer.\n\n"
                    "To continue your home buying journey, complete the mortgage pre-approval process.",
                    textAlign: TextAlign.center,
                    style: TextStyle(fontSize: 16),
                  ),

                  const SizedBox(height: 30),

                  SizedBox(
                    width: double.infinity,
                    height: 55,
                    child: ElevatedButton.icon(
                      icon: const Icon(Icons.assignment_turned_in),
                      label: const Text(
                        "Sign Pre-Approval",
                        style: TextStyle(fontSize: 18),
                      ),
                      onPressed: () async {

                        final response = await http.post(
                          Uri.parse("http://localhost:8080/preapproval"),
                          headers: {
                            "Authorization": "Bearer $jwtToken",
                            "Content-Type": "application/json",
                          },
                        );

                        if (response.statusCode == 200) {

                          await showDialog(
                            context: context,
                            builder: (_) => AlertDialog(
                              title: const Text("Congratulations! 🎉"),
                              content: const Text(
                                "Your mortgage pre-approval has been completed.\n\n"
                                "Please log in again to access your client dashboard.",
                              ),
                              actions: [
                                TextButton(
                                  onPressed: () {
                                    Navigator.pop(context);
                                  },
                                  child: const Text("OK"),
                                ),
                              ],
                            ),
                          );

                          Navigator.pushAndRemoveUntil(
                            context,
                            MaterialPageRoute(
                              builder: (_) => const LoginScreen(),
                            ),
                            (route) => false,
                          );

                        } else {

                          ScaffoldMessenger.of(context).showSnackBar(
                            SnackBar(
                              content: Text(response.body),
                            ),
                          );

                        }

                      },
                    ),
                  ),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}