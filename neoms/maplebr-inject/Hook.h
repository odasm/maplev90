#include "Windows.h"
#include "stdio.h";


namespace Hook {

	void hook(DWORD toHookAddy, DWORD jmpTo){
		DWORD dwProtect;
		VirtualProtect((DWORD*)toHookAddy, 100, PAGE_EXECUTE_READWRITE, &dwProtect);
		DWORD hookAddy = jmpTo;
		DWORD offset = jmpTo - toHookAddy - 5;
		BYTE input[5] = {
			0xE9, 0x00, 0x00, 0x00, 0x00
		};
		printf("PushAddy: %x\n", toHookAddy);
		printf("HookAddy: %x\n", jmpTo);
		printf("Calculating the offset: %x\n", offset);
		memcpy(&input[1], &offset, 4);
		memcpy((DWORD*)toHookAddy,&input, sizeof(input));
		VirtualProtect((DWORD*)toHookAddy, 100, PAGE_EXECUTE, &dwProtect);
	}


}