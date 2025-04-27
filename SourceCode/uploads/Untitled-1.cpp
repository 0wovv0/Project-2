#include <iostream>
#include <vector>

using namespace std;

const int MOD = 1e9 + 7;
int arr[1005][405];
int n, k1, k2;
int ans;

void input()
{
    cin >> n >> k1 >> k2;
    for (int i = 0; i <= k2; ++i)
    {
        arr[i][i] = 1;
    }
    arr[1][0] = 1;
}

int Solution()
{
    for (int i = 1; i <= n; ++i)
    {
        for (int j = 1; j <= k2; ++j)
        {
            arr[i][j] = arr[i - 1][j - 1];
        }
        for (int j = k1; j <= k2; ++j)
        {
            arr[i][0] += arr[i - 1][j];
        }
    }
    for (int i = k1; i <= k2; i++)
        ans += arr[n][i];
    ans += arr[n][0];
    return ans;
}

int main()
{
    input();
    cout << Solution();
    return 0;
}
