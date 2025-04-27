#include <iostream>

using namespace std;

int n;
int arr[1000000];
int arr2[1000000];

void merge_sort(int l, int r)
{
    if(l == r) return;
    merge_sort(l, (l+r)/2);
    merge_sort((l+r)/2 + 1, r);
    // Trộn
    int tmp;
    int i = l, j = (l+r)/2+1, pos = l;
    while(pos <= r)
    {
        if(i > (l+r)/2)
        {
            while(j <= r)
            {
                arr2[pos] = arr[j];
                j++;
                pos++;
            }
            break;
        }
        if(j > r)
        {
            while(i <= (l+r)/2)
            {
                arr2[pos] = arr[i];
                i++;
                pos++;
            }
            break;
        }
        
        if(arr[i] < arr[j]){
            arr2[pos] = arr[i];
            i++;
        }
        else{
            arr2[pos] = arr[j];
            j++;
        }

        pos++;
    }
    // Sắp xếp lại mảng
    for(i = l; i <= r; ++i)
    {
        arr[i] = arr2[i];
    }

}

void input()
{
    cin >> n;
    for(int i = 0; i < n; ++i)
    {
        cin >> arr[i];
    }
}

void printDS()
{
    for(int i = 0; i < n; ++i)
        cout << arr[i] << " ";
}

int main()
{
    input();
    merge_sort(0, n-1);
    printDS();
}
