// Online C compiler to run C program online
#include <stdio.h>

void fun(char *str) {
    int i =0;
    while (str[i] == 'a') {
        i++;
    }
    if(str[i] == 'b' && str[i+1] == 'b' && str[i+2] =='\0') {
        printf("valid String");
        return;
    }
    if(str[i] != 'a' || str[i] != 'b') {
        printf("Invalid String");
        return;
    }
    printf("Invalid String");
}

int main() {
    char str[100];
    printf("Enter string: ");
    scanf("%s", str);
    fun(str);

    return 0;
}
