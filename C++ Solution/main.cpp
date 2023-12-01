#include <iostream>
#include <cmath>

using namespace std;

#define eps 0.0001

double function1(double x, double y)
{
    return sin(x + y) - 1.2*x;
}

double function2(double x, double y)
{
    return x*x + y*y - 1;
}

double func11(double x, double y)
{
    return cos(x + y) - 1.2;
}

double func12(double x, double y)
{
    return cos(x + y);
}

double func21(double x, double y)
{
    return 2*x;
}

double func22(double x, double y)
{
    return 2*y;
}

void ober_matr(double a[2][2])
{
    double det, aa;
    det = a[0][0]*a[1][1] - a[0][1]*a[1][0];
    aa = a[0][0];
    a[0][0] = a[1][1]/det;
    a[1][1] = aa/det;
    aa = a[0][1];
    a[0][1] = -a[1][0]/det;
    a[1][0] = -aa/det;
}

void nuton(double x, double y)
{
    int i = 1;
    double a[2][2], dx, dy, b[2], norm;
    do
    {
        a[0][0] = func11(x, y);
        a[0][1] = func12(x, y);
        a[1][0] = func21(x, y);
        a[1][1] = func22(x, y);
        ober_matr(a);
        dx = -a[0][0]*function1(x, y) + -a[0][1]*function2(x, y);
        dy = -a[1][0]*function1(x, y) + -a[1][1]*function2(x, y);
        x = x + dx;
        y = y + dy;
        b[0] = function1(x, y);
        b[1] = function2(x, y);
        norm = sqrt(b[0]*b[0]+b[1]*b[1]);
        i++;
    }
    while (norm >= eps);
    cout << x << endl << y << endl;
}
int main()
{
    double x, y;
    cout << "x = ";
    cin >> x ;
    cout << "y = ";
    cin >> y;
    nuton(x, y);
    cout << endl;

    return 0;
}
