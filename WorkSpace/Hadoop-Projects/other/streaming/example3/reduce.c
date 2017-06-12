
#include <stdio.h>  
#include <stdlib.h>  
#include <string.h>  
  
#define BUFFER_SIZE     1024  
#define DELIM       "\t"  
  
int main(int argc, char * argv[])  
{  
     char str_last_key[BUFFER_SIZE];  
     char str_line[BUFFER_SIZE];  
     int count = 0;  
   
     *str_last_key = '\0';  
   
     while( fgets(str_line,BUFFER_SIZE-1,stdin) )  
     {  
         char * str_cur_key = NULL;  
         char * str_cur_num = NULL;  
   
         str_cur_key = strtok(str_line,DELIM);  
         str_cur_num = strtok(NULL,DELIM);  
   
         if(str_last_key[0] =='\0')  
         {  
             strcpy(str_last_key,str_cur_key);  
         }  
         if(strcmp(str_cur_key, str_last_key))// 前后不相等，输出  
         {  
             printf("%s\t%d\n",str_last_key,count);  
             count = atoi(str_cur_num);  
         }else{// 相等，则加当前的key的value  
             count += atoi(str_cur_num);  
         }  
         strcpy(str_last_key,str_cur_key);  
     }  
     printf("%s\t%d\n",str_last_key,count);  
     return 0;  
 } 

