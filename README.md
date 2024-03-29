<div ><h1> RISC-V FIVE Stage Pipeline Core</h1>
  
<div align='center'><img src="https://readme-typing-svg.demolab.com?font=Arial&size=22&pause=1000&color=8510d8&multiline=true&width=435&lines=RISC-V+FIVE Stage+Pipeline+Core" alt="Typing SVG" /><br>
<img align='center' src="https://readme-typing-svg.demolab.com?font=Arial&size=18&pause=1000&color=8510d8&multiline=true&width=435&lines=Designed+by+HAFSA+SHOAIB" alt="Typing SVG" />
</div>
<br><br>
  
First of all get started by cloning this repository on your machine.

```ruby
git clone https://github.com/Hafsa-shoaib989/5-Stage-Pipeline.git
```

Create a .txt file and place the ***hexadecimal*** code of your instructions simulated on ***Venus*** (RISC-V Simulator)\
Each instruction's hexadecimal code must be on seperate line as following. This program consists of 9 instructions.

```ruby
00500113
00500193
014000EF
00120293
00502023
00002303
00628663
00310233
00008067
```
Then perform the following step
```
cd 5-Stage-Pipeline/src/main/scala/PIPELINE
```
Open **INSTRmem.scala** with this command. You can also manually go into the above path and open the file in your favorite text editor.
```ruby
open INSTRmem.scala
```
Find the following line
``` python
loadMemoryFromFile(mem, C:/Users/Muhammad Sameed/Desktop/Desktop clean/Hafsa/Merl/input1.txt")
```
Change the .txt file path to match your file that you created above storing your own program instructions. or you can also use this file\
After setting up the INSTRmem.scala file, go inside the RV32i folder.
```ruby
And enter
sbt
```
When the terminal changes to this type
```ruby
sbt:RISCV-5-Stage-Pipeline>
```
Enter this command
```ruby
sbt:RISCV-5-Stage-Pipelin> testOnly practice1.PIPELINETest -- -DwriteVcd=1
```

After success you will get a folder ***test_run_dir*** on root of your folder. Go into the folder inside.\
There you will find the folder named Top. Enter in it and you can find the Top.vcd file which you visualise on **gtkwave** to\
see your program running.
