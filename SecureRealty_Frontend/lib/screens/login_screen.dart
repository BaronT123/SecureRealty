import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:securerealty/home_page.dart';
import 'signup_screen.dart';
import 'package:jwt_decoder/jwt_decoder.dart';
import 'package:securerealty/customer_home_page.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({super.key});

  @override
  State<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  final TextEditingController userController = TextEditingController();
  final TextEditingController passwordController = TextEditingController();

  bool hidePassword = true;
  bool loading = false;
  

  Future<void> login() async {
    if (userController.text.isEmpty || passwordController.text.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text("Please enter User ID and Password"),
        ),
      );
      return;
    }

    setState(() {
      loading = true;
    });

    try {
      final response = await http.post(
        Uri.parse("http://localhost:8080/login"),
        headers: {
          "Content-Type": "application/json",
        },
        body: jsonEncode({
          "email": userController.text,
          "password": passwordController.text,
        }),
      );

      if (response.statusCode == 200) {
        final responseBody = jsonDecode(response.body);
        final token = responseBody['token'] as String;
        final decodedToken = JwtDecoder.decode(token);
        final currentUser = decodedToken['sub']?.toString() ??
            decodedToken['email']?.toString() ??
            'user';
        String role = responseBody["role"];

        debugPrint('Decoded user: $currentUser');

        if (role == "CUSTOMER") {

  Navigator.pushReplacement(
    context,
    MaterialPageRoute(
      builder: (_) => CustomerHomePage(
        jwtToken: token,
      ),
    ),
  );

} else {

  Navigator.pushReplacement(
    context,
    MaterialPageRoute(
      builder: (_) => HomePage(
        jwtToken: token,
        currentUser: currentUser,
      ),
    ),
  );

}
      } else {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text("Invalid username or password"),
          ),
        );
      }
    } catch (e) {
  print(e);
  ScaffoldMessenger.of(context).showSnackBar(
    SnackBar(content: Text(e.toString())),
  );
}

    setState(() {
      loading = false;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("SecureRealty Login"),
      ),
      body: Center(
        child: SingleChildScrollView(
          padding: const EdgeInsets.all(24),
          child: SizedBox(
            width: 350,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.stretch,
              children: [
                const Icon(
                  Icons.lock,
                  size: 80,
                ),

                const SizedBox(height: 20),

                const Text(
                  "Welcome",
                  textAlign: TextAlign.center,
                  style: TextStyle(
                    fontSize: 28,
                    fontWeight: FontWeight.bold,
                  ),
                ),

                const SizedBox(height: 30),

                TextField(
                  controller: userController,
                  decoration: const InputDecoration(
                    labelText: "User ID",
                    border: OutlineInputBorder(),
                    prefixIcon: Icon(Icons.person),
                  ),
                ),

                const SizedBox(height: 20),

                TextField(
                  controller: passwordController,
                  obscureText: hidePassword,
                  decoration: InputDecoration(
                    labelText: "Password",
                    border: const OutlineInputBorder(),
                    prefixIcon: const Icon(Icons.lock),
                    suffixIcon: IconButton(
                      icon: Icon(
                        hidePassword
                            ? Icons.visibility_off
                            : Icons.visibility,
                      ),
                      onPressed: () {
                        setState(() {
                          hidePassword = !hidePassword;
                        });
                      },
                    ),
                  ),
                ),

                const SizedBox(height: 30),

                SizedBox(
                  height: 50,
                  child: ElevatedButton(
                    onPressed: loading ? null : login,
                    child: loading
                        ? const CircularProgressIndicator()
                        : const Text(
                            "Login",
                            style: TextStyle(fontSize: 18),
                          ),
                  ),
                ),
                const SizedBox(height: 20),

                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [

                    const Text("Don't have an account?"),

                    TextButton(

                      onPressed: () {

                        Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder: (_) => const SignupScreen(),
                          ),
                        );

                      },

                      child: const Text("Sign Up"),

                    )

                  ],
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}