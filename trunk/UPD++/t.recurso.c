int bubble(int a[], int n){
	int i,k;
	i=n-1; k=0;
	while (i>0){
		if(a[i]<a[i-1]) {swap(a,i,i-1);k++;}
		i--;
	}
	return k;
}

void bsort (int a[], in  n){
	while(bubble(a,n));
}
 int i,k;
	i=n-1; k=0;
	while (i>0){
		if(a[i]<a[i-1]) {swap(a,i,i-1);k++;}
		i--;
	}
	return k;
}

void bsort (int a[], in 