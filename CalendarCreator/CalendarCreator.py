# -*- coding: utf-8 -*-
"""
Created on Thu Oct  6 20:36:55 2022
# A1
# Name: Kirsten Sotelo
# Student ID: 501169612

Problem: 
    The user will input a year and the program should print the whole year's calendar.
    The goal is to not import anything (whether it be the dates, or months, or the calendar method itself), and to create from scratch
    The year input by the user should only be between 2000 and 2030 inclusive.

"""

# Defining a months list containing all list from January to December, starting at index 1 and ending at index 12 (index 0 is a place holder, for easier iteration)
#           IND 0      IND 1   IND 2       IND 3   IND 4   IND 5   IND 6   IND 7   IND 8       IND 9       IND 10      IND 11      IND 12
months = ["INDEX 0", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"]

# Defining years based on what day of the week is January 1 in the following sets. This will act as a database, as all the information required to for the program to work is what day of the week is January 1 of said year. 
# 2006, 2012, 2017, 2013 and 2023 all have January 1 on Sunday
sunday=set([2006, 2012, 2017, 2013, 2023])
# 2001, 2007, 2018, 2024 and 2029 all have January 1 on Monday
monday=set([2001, 2007, 2018, 2024, 2029])
# 2002, 2008, 2013, 2019 and 2030 all have January 1 on Tuesday
tuesday=set([2002, 2008, 2013, 2019, 2030])
# 2003, 2014, 2020 and 2025 all have January 1 on Wednesday
wednesday=set([2003, 2014, 2020, 2025])
# 2004, 2009, 2015 and 2026 all have January 1 on Thursday
thursday=set([2004, 2009, 2015, 2026])
# 2010, 2016, 2021 and 2027 all have January 1 on Friday
friday=set([2010, 2016, 2021, 2027])
# 2000, 2005, 2011, 2022 and 2028 all have January 1 on Saturday
saturday=set([2000, 2005, 2011, 2022, 2028])


def calendar(year):
    # If statement to check if the year inputted by the user is in the 'day of the week' sets
    # Checking if the year inputted by the user is in the 'sunday' set,  if true, January 1 will start on sunday
    if year in sunday: day = 1
    # Checking if the year inputted by the user is in the 'monday' set,  if true, January 1 will start on monday
    elif year in monday: day = 2
    # Checking if the year inputted by the user is in the 'tuesday' set,  if true, January 1 will start on tuesday
    elif year in tuesday: day = 3
    # Checking if the year inputted by the user is in the 'wednesday' set,  if true, January 1 will start on wednesday
    elif year in wednesday: day = 4
    # Checking if the year inputted by the user is in the 'thursday' set,  if true, January 1 will start on thursday
    elif year in thursday: day = 5
    # Checking if the year inputted by the user is in the 'friday' set,  if true, January 1 will start on friday
    elif year in friday: day = 6
    # Checking if the year inputted by the user is in the 'saturday' set,  if true, January 1 will start on saturday
    elif year in saturday: day = 7
    else:
        # This else should never happen, as all the years from 2000 to 2030 inclusive should be in the "database", if not, it will not pass the 'if' statement of if year >= 2000 and year <= 2030
        pass
    # day-=2 is set in place because the iterations used below initially start at 0, instead of 1 -> This is for fixing iterations
    day-=2
    
    # Setting 'leap' to a boolean value
    leap=bool
    # The criteria for a leap year is that the number is divisible by 4. If the number is divisible by 100, it would not be a leap year, but if it is both divisible by 100 and 400 it is a leap year.
    # Ex: 2000 is a leap because it is divisible both by 100 and 400. If 1900 was part of the database, it would not be a leap year because it is not divisible by 400
    if year%4==0 and (year%100!=0 or year%400==0):
        #Set leap to True
        leap = True
    else:
        #Else: set leap to False
        leap = False
    
    # Parent 'if' statement to detect if the year input by the user is between 2000 and 2030 inclusive, as they are the only years included in the 'database' (the sets above)
    if year >= 2000 and year <= 2030:
        # First line of formatting calendar
        print(f"{year} Calendar")
        # 'for' loop to count from 1 to 12, to print the 12 months of the calendar (This skips 0 because index 0 is not a real month)
        for x in range(1,13):
            # Printing the Calendar Format
            monthString = (months[x])
            print(f"{monthString}\nSun\tMon\tTue\tWed\tThu\tFri\tSat")
            
            # PART 1: Determining how many days in the month there are, which is used for later iterations
            # ////////////////////////////////////////////////////////////////////////////////////
            # If the monthString is equal to either 'January', 'March', 'May', 'July', 'August', 'October',  or 'December', there will be 31 days
            if monthString=="January" or monthString=="March" or monthString=="May" or monthString=="July" or monthString=="August" or monthString=="October" or monthString=="December":
                dayInMonths = 31
            # If the monthString is equal 'February', there will be a nested if statement
            elif monthString=="February":
                # If the 'leap' boolean value is True(from the statement earlier):
                if leap == True:
                    #There will be 29 days in February
                    dayInMonths=29
                #If the 'leap' boolean value is False:
                elif leap == False:
                    # There will be 28 days in February
                    dayInMonths=28
            else:
                # And for all the other months not listed, will have 30 days, as the day in months can only be 28 or 29(if it's february), 30, or 31
                dayInMonths=30
            
            
            
            # PART 2: Printing numbers in calendar in the right spots using the 'for' loop below
            # ///////////////////////////////////////////////////////////////////////////
            # Counts from i (initially starts at 0) to the number of dayInMonths(the value above) + the number of days(the day that it starts) + 1 (this +1 is to include the last day; without this, it would result in a missing day in every month)
            for i in range((day+dayInMonths)+1):
                # 'day' is the value that determines what day of the week the month's 1st day should start.
                # The if statement below is to check if the count is lower than the day
                if(i<=day):
                    # If true, then there will be an indent, to essentially count how many days to 'skip'
                    # Ex: If day = 2 (Which is a tuesday), there will be 2 empty indents (sunday and monday)
                    print('', end='\t')
                #Else statement for after the count of indents is finished
                else:
                    # Once the 'if' statement detects that the count is higher than the 'day', print the count starting from 1.
                    # Essentially, 'i' is the hidden counter of the total indents + days in the months; therefore (i-day) is used to print starting from 1
                    print((i-day), end='\t')
                    # If statement to check if the count for 'i'+1 is divisible by 7; (i+1) is used because i initially starts at 0
                    if((i+1) % 7 == 0):
                        # if true, start a new line; this is used for proper formatting of the calendar, to keep indenting after the 'saturdays' of the calendar
                        print()
            
            
            
            # PART 3: Assinging 'day' for the next iteration (for the next month); to set which day the next month starts
            # ///////////////////////////////////////////////////////////////////////////////////
            # the 'if' statement is to detect if the count 'i' after the end of the month is applicable to either of these cases
            # i==34 and i%7==6 is placed due to a bug. This essentially happens when the last day of the month is on a Saturday. Without this 'if' statement, the program will still run but there will be printing bug
            if(i==34 or i%7==6):
                #If true, set the 'day' for the next iteration to be the remainder of the (i / 7) - 7.
                day=(i%7)-7
            else:
                #If the count for i of the two cases above are both false, default to set 'day' to i%7
                day=i%7
            # i%7 returns the remainder of the 'i' counter divided by 7.
            # Ex: if the month starts on a sunday and ends on a tuesday, the count 'i' should be 30. 30%7 = 2. Therefore in the next iteration, there should be 3 indents (skips Sunday, Monday, and Tuesday and therefore starts on Wednesday); 
            # However the remainder is 2, there is 3 indents because the 'for' loop starts counting at 0. So it counts 0, 1, 2, (3 counts for each indent)
               
            # A formatting print line, to create space between months
            print("\n\n")
    else:
        # The parent if statement is to check if the argument is within range of 2000 - 2030. If it is not in range, it will print so
        print("This year is not in the database.")
                
if __name__ == "__main__":
    # Calling the methods
    # Printing a calendar for 2022
    #calendar(2022)
    #print("\n\n\n")
    # Printing another calendar for 2023
    #calendar(2023)

    yearInput = input("Please enter a year: ")
    calendar(int(yearInput))