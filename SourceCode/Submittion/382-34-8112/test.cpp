#include <iostream>
#include <fstream>
#include <map>

using namespace std;

int main() {
    string s;
    map<string, int> mapp;
    fstream readFile("C:/Users/hokta/Downloads/TestFile.txt");
    
    if (!readFile.is_open()) {
        cout << "Khong the mo file";
    } else {
        while (readFile >> s) {
        	if(s[s.size() - 1] == '.'){
        		s.pop_back();
			}
            if (mapp.count(s) == 0) {
                mapp[s] = 1;
            } else {
                mapp[s]++;
            }
        }
    }
    
    map<string, int>::iterator it = mapp.begin();
    while (it != mapp.end()) {
        cout << it->first << " // " << it->second << endl;
        it++;
    }

    return 0;
}