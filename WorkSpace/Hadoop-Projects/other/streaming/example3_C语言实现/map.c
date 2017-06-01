
#include <stdio.h>  
#include <stdlib.h>  
#include <string.h>  
  
#define BUF_SIZE    2048  
#define DELIM       '\n'  
  
int main(int argc, char * argv[])  
{  
     char buffer[BUF_SIZE];  
     while(fgets(buffer,BUF_SIZE-1,stdin))  
     {  
         int len = strlen(buffer);  
         if(buffer[len-1] == DELIM) // ½«»»ÐÐ·ûÈ¥µô  
             buffer[len-1] = 0;  
   
         char *query = NULL;  
         query = strtok(buffer, " ");  
         while(query)  
         {  
             printf("%s\t1\n",query);  
             query = strtok(NULL," ");  
         }  
     }  
     return 0;  
 }  

