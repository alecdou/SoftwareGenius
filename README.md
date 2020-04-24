# Software Genius

## *Other documents*
- [Software Requirement Specification (SRS)](https://github.com/DouMaokang/SoftwareGenius/blob/master/Doc/SoftwareGenius%20SRS.pdf)
- Design Document
  1. [System Architecture](https://github.com/DouMaokang/SoftwareGenius/blob/master/Doc/ArchitectureDiagram.png)
  2. [Architecture Analysis](https://github.com/DouMaokang/SoftwareGenius/blob/master/Doc/Architecutre%20Analysis.pdf)
  3. [Class Diagram/Design](https://github.com/DouMaokang/SoftwareGenius/blob/master/Doc/ClassDiagram.png)
  4. [State Transition Diagram](https://github.com/DouMaokang/SoftwareGenius/blob/master/Doc/DialogMap.png)
- [Test Cases and Test Summary](https://github.com/DouMaokang/SoftwareGenius/blob/master/Doc/Test%20Summary.pdf)


## Description
SoftwareGenius is a mobile-based online educational game system. This system is created to gamify and socialize teaching and learning of software engineering courses, catering for the demand of The Teaching, Learning and Pedagogy Division (TLPD) of NTU.

## Purpose
SoftwareGenius is a free educational mobile game which aims to teach students knowledge about software engineering. It is not only a simple educational mobile application, but also an exciting game where students can fight against enemies, exploit their territories and experience four different phases of software engineering including requirements engineering, architecture design, implementation and software testing.

## Photo Gallery
<p align="center">
  <img src="https://github.com/DouMaokang/SoftwareGenius/blob/master/Doc/img1.png" width="400">
  <img src="https://github.com/DouMaokang/SoftwareGenius/blob/master/Doc/img2.png" width="400">
  <img src="https://github.com/DouMaokang/SoftwareGenius/blob/master/Doc/img3.png" width="400">
  <img src="https://github.com/DouMaokang/SoftwareGenius/blob/master/Doc/img4.png" width="400">
</p>



## Instruction for Running the Program
1. Download the project to your local machine
2. Open Unity project in Unity
  - Unity version recommendation(for the best possible compatibility) : 2019.3.2f1
If you correctly setup backend application at localhost, remember to change url variable in the file "Assets/SourceCode/Control/StaticVariable.cs"
url = "http://localhost:9090/api/"
- Building Android apk package prerequisite: 
  - install Android SDK and JAVA JDK (recommended version JDK 11 or below);
  - change url of server to your tunnel server similar to the above situation: "Assets/SourceCode/Control/StaticVariable.cs"
url = YourServerLink+"/api/"
3. Open JavaBackend in IntelliJ or other IDEs
