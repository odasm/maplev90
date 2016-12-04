#include "Windows.h";
#include "stdio.h";
#include "Addy.h"
#include "Hook.h"

typedef HRESULT(*originalDirectInput8Create)(HINSTANCE, DWORD, REFIID, LPVOID*, LPUNKNOWN); 
originalDirectInput8Create originalFunction = NULL;



#define ENABLE_CONSOLE 0;

LPCSTR vote_url = "http://www.mapleclassic.me/?base=main";


__declspec(naked) void openWebSite(){
	ShellExecute(0, 0, vote_url, 0, 0 , SW_SHOW );
	__asm{
		pop ecx
		jmp Addy::VOTE_WEBSITE_URL_RET
	}
}


void installVotePageHook(){
	Hook::hook(Addy::VOTE_WEBSITE_URL_HOOK, (DWORD)&openWebSite);
	
}

extern "C"   __declspec(dllexport) HRESULT DirectInput8Create(
         HINSTANCE hinst,
         DWORD dwVersion,
         REFIID riidltf,
         LPVOID * ppvOut,
         LPUNKNOWN punkOuter
		 ){
	if(originalFunction == NULL){
		HMODULE hHandle = LoadLibraryA("dinput8.dll");
		FARPROC proc = GetProcAddress(hHandle, "DirectInput8Create");
		originalFunction = (originalDirectInput8Create)(proc);
		printf("Initialized DirectInput8Create hook");	
	}
	
	return originalFunction(hinst, dwVersion, riidltf, ppvOut, punkOuter);

}




BOOL WINAPI DllMain(HMODULE hModule, DWORD dwReason, LPVOID lpvReserved) { 
	switch ( dwReason ) { 
		case DLL_PROCESS_ATTACH:
			DisableThreadLibraryCalls(hModule);	
			#if ENABLE_CONSOLE
				AllocConsole();
			#endif
			CreateThread(0,0,(LPTHREAD_START_ROUTINE)&installVotePageHook,0,0,0);
			break; 
		case DLL_PROCESS_DETACH:
			ExitProcess(0); 
			break;
		case DLL_THREAD_ATTACH:
			break;
		case DLL_THREAD_DETACH:
			break;
		}
	return TRUE; 
}