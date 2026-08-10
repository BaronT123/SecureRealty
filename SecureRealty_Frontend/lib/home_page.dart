import 'package:flutter/material.dart';

import 'package:securerealty/screens/conversations_screen.dart';
import 'package:securerealty/screens/dashboard_screen.dart';
import 'package:securerealty/screens/documents_screen.dart';
import 'package:securerealty/screens/profile_screen.dart';

class HomePage extends StatefulWidget {
  final String jwtToken;
  final String currentUser;
  const HomePage({super.key, required this.jwtToken, required this.currentUser});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  int currentIndex = 0;

  late final List<Widget> pages;

  @override
  void initState() {
    super.initState();

    pages = [
      const DashboardScreen(),
      DocumentScreen(jwtToken: widget.jwtToken),
      ConversationsScreen(
  jwtToken: widget.jwtToken,
  currentUser: widget.currentUser,
),
      const ProfileScreen(),
    ];
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('SecureRealty'),
        centerTitle: true,
      ),
      body: pages[currentIndex],
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: currentIndex,
        onTap: (index) {
          setState(() {
            currentIndex = index;
          });
        },
        type: BottomNavigationBarType.fixed,
        items: const [
          BottomNavigationBarItem(
            icon: Icon(Icons.dashboard),
            label: 'Dashboard',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.folder),
            label: 'Documents',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.chat),
            label: 'Chat',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.person),
            label: 'Profile',
          ),
        ],
      ),
    );
  }
}
