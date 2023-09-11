package com.example.webservices.restservices.hello;

import static com.example.webservices.restservices.hello.HelloWorld.SpeedConverter.*;

public class HelloWorld {

	public static void main(String [] args) {
		System.out.println("Hello World");

		/*boolean gameOver = true;
		int score = 800;
		int levelCompleted = 5;
		int bonus = 100;

		if (score < 5000 && score > 1000) {
			System.out.println("Your score was less than 5000 but greater than 1000");
		} else if (score < 1000) {
			System.out.println("Your score was less than 1000");
		} else {
			System.out.println("Got here");
		}*/


	  checkNumber(0);
	  checkNumber(1);
	  checkNumber(-1);

	  printConversion(-1);

	  printMegaBytesAndKiloBytes(2500);

	  shouldWakeUp(true,11);

	 areEqualByThreeDecimalPlaces(3.156,3.157);

	  sumChecker(1,1,3);

	  teenNumber(19,20,21);

	  boolean newTeen = isTeen(15);
		System.out.println("teen number" + newTeen);

		boolean gameOver = true;
		int score = 800;
		int levelCompleted = 5;
		int bonus = 100;

		int finalScore = score;

		calculateScore(true, 800, levelCompleted, bonus);


	/*	score = 10000;
		levelCompleted = 8;
		bonus = 200;

		finalScore = score;*/

		/*if (gameOver) {
			finalScore += (levelCompleted * bonus);
			System.out.println("Your final score was " + finalScore);
		}*/
		/*toMilesPerHour(-1);*/
	}

	public static void calculateScore(boolean gameOver, int score, int levelCompleted, int bonus) {

		int finalScore = score;

		if (gameOver) {
			finalScore += (levelCompleted * bonus);
			finalScore += 1000;
			System.out.println("Your final score was " + finalScore);
		}

		/*short  myShortValue = Short.MIN_VALUE;
		System.out.println("value is " + myShortValue);*/

	/*	byte first = 1 , second=2;
		System.out.println(first);
		System.out.println(second);*/

	/*	byte newByte = 5;
		short newShort = 6;
		int newInt = 7;
		long newLong = 50000 + 10*(newInt+newByte+newShort);
		System.out.println("sum is " + newLong);*/

		/*if(newInt == 6){
			System.out.println("true");
		}
		else{
			System.out.println("false");
		}*/




	}

	//PositiveNegativeZero Program
	public static void checkNumber (int number){
		if(number>0){
			System.out.println("positive");
		} else if (number < 0 ) {
			System.out.println("negative");
		}
		else{
			System.out.println("zero");
		}
	}

	/*public static void toMilesPerHour(long kilometersPerHour){

		if(kilometersPerHour < 0){
			System.out.println("-1");
		}
*/

	//Speed Converter Program
	public class SpeedConverter {

		public static long toMilesPerHour(double kilometersPerHour){

			if (kilometersPerHour <0) {
				return -1;
			}

			return Math.round(kilometersPerHour/1.609);

		}

		public static void printConversion (double kilometersPerHour) {

			if (kilometersPerHour <0) {
				System.out.println("Invalid Value");
			} else {
				long mPH = toMilesPerHour(kilometersPerHour);
				System.out.println(kilometersPerHour + " km/h = " + mPH + " mi/h");
			}

		}


		//MegaByteConverter Program
		public static void printMegaBytesAndKiloBytes(int kiloBytes){

			if (kiloBytes < 0){
				System.out.println("Invalid Value");
			} else {
				int megabytes = (kiloBytes/1024);
				int kiloRemainder = kiloBytes%1024;
				System.out.println(kiloBytes+" KB = "+megabytes+" MB and "+kiloRemainder+" KB");
			}

		}

		//DogBarking Program
		public static boolean shouldWakeUp(boolean barking,int hourOfDay){

			if (hourOfDay <0 || hourOfDay >23){
				return false;
			}

			if (barking == true && hourOfDay <8 || hourOfDay >22){
				return true;
			}else{
				return false;
			}
		}


		//LeapYear Program
		public static boolean isLeapYear(int year){
			if(year<1 || year >9999){
				return false;
			}
			if (year % 4 == 0) {
				if (year % 100 == 0) {
					if (year % 400 == 0) {
						return true;
					}
					return false;
				}
				return true;
			}
			return false;
		}


		//DecimalComparator
		//Ex: 3.156,3.157 -> muliply by 1000 . 3156 and 3157 .
		// type cast from double to int and check if both are matching
		public static void areEqualByThreeDecimalPlaces(double first,double second) {

			if (first <0 && second >0 || first >0 && second <0){
				System.out.println("false");
			}

			double firstThousand = first*1000;
			double secondThousand = second*1000;

			int firstint = (int)firstThousand;
			int secondint = (int)secondThousand;

			if (firstint==secondint){
				System.out.println("true");
			}
            else {
				System.out.println("false");
			}
		}

		//SumChecker Program
		public static void sumChecker(int a, int b, int c){
			if(a+b==c){
				System.out.println("true");
			}
			else{
				System.out.println("false");
			}
		}

		//Teen Number Checker Program
		public static void teenNumber(int num1,int num2,int num3){
			if ((num1 >= 13 && num1 <= 19) || (num2 >= 13 && num2 <= 19) || (num3 >= 13 && num3 <= 19)){

				System.out.println("teen number");
			}
			else {
				System.out.println("not a teen number");
			}
		}

		public static boolean isTeen(int num4){
			if(num4>=13 || num4<=19){

				return true;
			}
			return false;
		}
	}

	}
