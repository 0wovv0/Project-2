#include <iostream>
#include <vector>
using namespace std;

int n, m, countt, opt;
int arr[1000000];
int length[1000000];

void inputData()
{
    cin >> n;
    for(int i = 0; i < n; ++i)
    {
        cin >> arr[i];
    }
}

void solution()
{
    opt = 0;
    for(int i = 0; i < n; ++i){
        length[i] = 1;
        for(int j = i - 1; j >= 0; --j)
        {
            if(arr[i] == arr[j] + 1)
            {
                length[i] =  length[j] + 1;
                if(length[i] > opt) opt = length[i];
                break;
            }    
        }
    }
    cout << "Answer: " << opt;
}

int main()
{
    ios_base::sync_with_stdio(false);
    cin.tie();
    cout.tie();
    cin >> countt;
    while (countt > 0)
    {
        countt--;
        inputData();
        solution();
    }
    
    return 0;
}