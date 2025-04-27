#include <iostream>

using namespace std;

int main(){
/*	int a =10;
	cout<<&a<<endl;
	
	int *ptr = new int();
	*ptr = 5;
	cout<<ptr<<endl;
	
	int *newptr = ptr;
	cout<<newptr<<endl;
	
	delete newptr;
	cout<<newptr;
	cout<<*ptr<<endl;
	cout<<newptr;
*/	
	int n = 3, m = 4;
	
	int** arr[n];
	//Nhap du lieu
	for(int i = 0; i < n; ++i){
		arr[i] = new int*[m];
		for(int j = 0; j < m; ++j)
		cin>>arr[i][j];
	}
		
	//tinh tung hang
	for(int i = 0; i < n; ++i){
		sum = 0;
		for(int j = 0; j < m; ++j)
			sum += arr[i][j];
		cout<<sum<<endl;
	}
		
	delete[] arr;	
}
