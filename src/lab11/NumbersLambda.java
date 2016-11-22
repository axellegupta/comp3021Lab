package lab11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class NumbersLambda {

	// Find numbers with certain properties in a unified form
	// The property is specified in Predicate

	public static List<Integer> findNumbers(List<Integer> list, Predicate<Integer> predicate) {
		List<Integer> results = new ArrayList<Integer>();
		for (int n : list) {
			if (predicate.test(n)) results.add(n);
		}
		return results;
	}

	public static List<Integer> calculateScore(List<Integer> list, Function<Integer, Integer> function) {
		List<Integer> results = new ArrayList<Integer>();
		for (int n : list) {
			int score = function.apply(n);
			results.add(score);
		}
		return results;
	}

	public static Predicate<Integer> isOdd() {
		return x -> x % 2 != 0;
	}

	public static Predicate<Integer> isPrime() {

		return x -> {boolean add=true;for(int i=2;i<x/2;++i){
			if (x % i == 0 && (i!=x)) {add = false; break;}
		}
		return add;};	
	}


	public static Predicate<Integer> isPalindrome() {
		return x -> {
			boolean add = false;
			int rev=0;
			int pal = x;
			while(pal!=0){
				int temp = pal % 10;
				rev = rev*10 + temp;
				pal  = pal/10;
			}
			if(x==rev)
			{add= true;}
			return add;};
	}

	public static Function<Integer, Integer> policy() {
		return x -> {
			int score=0;
			if (isOdd().test(x)) 
				score+=1;
			if (isPrime().test(x))
				score+=2;
			if (isPalindrome().test(x))
				score+=4;
			return score;};
	}

	public static void main(String[] args) {
		List<Integer> numbers = Arrays.asList(480,514,484,389,709,935,328,169,649,300,685,429,243,532,308,87,25,282,91,415);

		System.out.println("The odd numbers are : " + findNumbers(numbers,isOdd()));
		System.out.println("The prime numbers are : " + findNumbers(numbers,isPrime()));
		System.out.println("The palindrome numbers are : " + findNumbers(numbers,isPalindrome()));

		System.out.println("The score of the all numbers are :" + calculateScore(numbers, policy()));
	}
}