# Synchronization OS project 
#### In this project I will create a multithreaded search application.
#### The program will allow searching for all files with a specific extension in a root directory.
#### Files with the specific extension will be copied to a specified directory.

![image](https://user-images.githubusercontent.com/70089477/121814726-352b3580-cc7b-11eb-8208-b5cfaabe982e.png)

</HEAD>

<BODY BGCOLOR="white" onload="windowTitle();">
<HR>

<HR>
<!-- ======== START OF CLASS DATA ======== -->
<H2>
Class Scouter</H2>
<PRE>
java.lang.Object
  <IMG SRC="./resources/inherit.gif" ALT="extended by "><B>Scouter</B>
</PRE>
<DL>
<DT><B>All Implemented Interfaces:</B> <DD>java.lang.Runnable</DD>
</DL>
<HR>
<DL>
<DT><PRE>public class <B>Scouter</B><DT>extends java.lang.Object<DT>implements java.lang.Runnable</DL>
</PRE>

<P>
A scouter thread
 This thread lists all sub-directories from a given root path. Each sub-directory is
 enqueued to be searched for files by Searcher threads.
<P>

<P>
<HR>

<P>

<!-- ======== CONSTRUCTOR SUMMARY ======== -->

<A NAME="constructor_summary"><!-- --></A>
<TABLE BORDER="1" WIDTH="100%" CELLPADDING="3" CELLSPACING="0" SUMMARY="">
<TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
<TH ALIGN="left" COLSPAN="2"><FONT SIZE="+2">
<B>Constructor Summary</B></FONT></TH>
</TR>
<TR BGCOLOR="white" CLASS="TableRowColor">
<TD><CODE><B><A HREF="Scouter.html#Scouter(SynchronizedQueue, java.io.File)">Scouter</A></B>(int id, <A HREF="SynchronizedQueue.html" title="class in &lt;Unnamed&gt;">SynchronizedQueue</A>&lt;java.io.File&gt;&nbsp;directoryQueue,
        java.io.File&nbsp;root, SynchronizedQueue</A>&lt;String&gt;&nbsp;milestonesQueue, boolean isMilestones)</CODE>

<BR>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Construnctor.</TD>
</TR>
</TABLE>
&nbsp;
<!-- ========== METHOD SUMMARY =========== -->

<A NAME="method_summary"><!-- --></A>
<TABLE BORDER="1" WIDTH="100%" CELLPADDING="3" CELLSPACING="0" SUMMARY="">
<TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
<TH ALIGN="left" COLSPAN="2"><FONT SIZE="+2">
<B>Method Summary</B></FONT></TH>
</TR>
<TR BGCOLOR="white" CLASS="TableRowColor">
<TD ALIGN="right" VALIGN="top" WIDTH="1%"><FONT SIZE="-1">
<CODE>&nbsp;void</CODE></FONT></TD>
<TD><CODE><B><A HREF="Scouter.html#run()">run</A></B>()</CODE>

<BR>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Starts the scouter thread.</TD>
</TR>
</TABLE>
&nbsp;<A NAME="methods_inherited_from_class_java.lang.Object"><!-- --></A>
<TABLE BORDER="1" WIDTH="100%" CELLPADDING="3" CELLSPACING="0" SUMMARY="">
<TR BGCOLOR="#EEEEFF" CLASS="TableSubHeadingColor">
<TH ALIGN="left"><B>Methods inherited from class java.lang.Object</B></TH>
</TR>
<TR BGCOLOR="white" CLASS="TableRowColor">
<TD><CODE>equals, getClass, hashCode, notify, notifyAll, toString, wait, wait, wait</CODE></TD>
</TR>
</TABLE>
&nbsp;
<P>

<!-- ========= CONSTRUCTOR DETAIL ======== -->

<A NAME="constructor_detail"><!-- --></A>
<TABLE BORDER="1" WIDTH="100%" CELLPADDING="3" CELLSPACING="0" SUMMARY="">
<TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
<TH ALIGN="left" COLSPAN="1"><FONT SIZE="+2">
<B>Constructor Detail</B></FONT></TH>
</TR>
</TABLE>

<A NAME="Scouter(SynchronizedQueue, java.io.File)"><!-- --></A><H3>
Scouter</H3>
<PRE>
public <B>Scouter</B>(<A HREF="SynchronizedQueue.html" title="class in &lt;Unnamed&gt;">int id, SynchronizedQueue</A>&lt;java.io.File&gt;&nbsp;directoryQueue,
               java.io.File&nbsp;root, SynchronizedQueue</A>&lt;String&gt;&nbsp;milestonesQueue, boolean isMilestones)</PRE>
<DL>
<DD>Construnctor. Initializes the scouter with a queue for the directories to
 be searched and a root directory to start from.
<P>
<DL>
<DT><B>Parameters:</B><DD><CODE>id</CODE> - the id of the thread running the instance<DD><CODE>directoryQueue</CODE> - A queue for directories to be searched<DD><CODE>root</CODE> - Root directory to start from<DD><CODE>milestonesqueue</CODE> a synchronizedQueue to write milestones to<DD><CODE><CODE>isMilestones</CODE> - indicating whether or not the running thread should write to the milestonesQueue<DD><CODE></DL>
</DL>

<!-- ============ METHOD DETAIL ========== -->

<A NAME="method_detail"><!-- --></A>
<TABLE BORDER="1" WIDTH="100%" CELLPADDING="3" CELLSPACING="0" SUMMARY="">
<TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
<TH ALIGN="left" COLSPAN="1"><FONT SIZE="+2">
<B>Method Detail</B></FONT></TH>
</TR>
</TABLE>

<A NAME="run()"><!-- --></A><H3>
run</H3>
<PRE>
public void <B>run</B>()</PRE>
<DL>
<DD>Starts the scouter thread. Lists directories under root directory and adds
 them to queue, then lists directories in the next level and enqueues them
 and so on.
 This method begins by registering to the directory queue as a producer and
 when finishes, it unregisters from it.
 If the isMilestones was set in the constructor (and therefore the milstonesQueue was sent to it as well, it should write every "important" action to this queue.
<P>
<DD><DL>
<DT><B>Specified by:</B><DD><CODE>run</CODE> in interface <CODE>java.lang.Runnable</CODE></DL></DD>
<!-- ========= END OF CLASS DATA ========= -->
  
</HEAD>


