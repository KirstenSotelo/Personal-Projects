class Methods(object):
    # ----------------------------------------------------------
    # HELPER FUNCTIONS
    # -------------------------------------------------------

    def reverse(self, arr, start, end):
        while start < end:
            arr[start], arr[end] = arr[end], arr[start]
            start += 1
            end -= 1

    # FUNCTIONS

    def removeElement(self, nums, val):

        i,k = 0,0

        # While i < len(nums),
        # if the int at nums[i] is the val, remove it, increment k,
        # If it does not remove it, increment i

        print(nums)

        while i < len(nums):
            if nums[i] == val:
                nums.remove(nums[i])
                k+=1
                print("i: %i, nums: %a" %(i, nums))
            else:
                print("i: %i, nums: %a" %(i, nums))
                i+=1

    def removeDuplicates(self, nums):
        """
        :type nums: List[int]
        :rtype: int
        """
        
        i=0
        temp = []

        while i < len(nums):
            if nums[i] not in temp:
                temp.append(nums[i])
                i+=1
                print(nums)
            else:
                # NOTE: Pop removes at index, not the element
                nums.pop(i)
                print(nums)

    def removeDuplicates2(self, nums):
        """
        :type nums: List[int]
        :rtype: int
        """
        # Garbage solution ngl, but works

        temp=[]
        temp2=[]
        temp3=[]
        i=0

        while i < len(nums):
            if not (nums[i] in temp):
                temp.append(nums[i])
                i+=1
                print(nums)
                continue

            if not (nums[i] in temp2):
                temp2.append(nums[i])
                i+=1
                print(nums)
                continue

            if not (nums[i] in temp3):
                temp3.append(nums[i])
                nums.pop(i)
                print(nums)
                continue

            if nums[i] in temp3:
                nums.pop(i)
                print(nums)

    def majorityElement(self, nums):
        """
        :type nums: List[int]
        :rtype: int
        """
        # This method ONLY works if the majority exists, it does NOT count the most common element

        majority = None
        count = 0

        # iterate through nums, if count is 0, set the majority to the element
        for i in nums:
            if count == 0:
                majority = i

            # count increments by 1 if the element is majority, else, decr by 1,
            # if count gets to 0, set a new majority
            if i == majority:
                count+=1
            else:
                count-=1
            print(count)
            

        print("m: ", majority)
        return majority
    
    def rotate(self, nums, k):
        # This does not modify nums in place, oops
        # I did not read it

        kToEnd = k+1
        countToK = 0
        lenMinusK = len(nums) - k
        temp = []

        while kToEnd < len(nums):
            temp.append(nums[kToEnd])
            kToEnd+=1
        
        while countToK < lenMinusK:
            temp.append(nums[countToK])
            countToK+=1

        print(temp)
        return temp
    
    def rotate2(self, nums, k):
        temp1=0
        temp2=0
        countToLen=0
        countToK = 0
        kToLen = k

        print(nums)
        while kToLen < len(nums):
            if countToK < k:
                temp1 = nums[countToK]
                temp2 = nums[len(nums)-k + countToK]
                nums[countToK] = nums[len(nums)-k + countToK]
                nums[kToLen] = temp1
                countToK+=1
                print(nums)
            
            kToLen+=1
        pass    


    def rotate3(self, nums, k): # ChatGPT ANSWER: actually kind of clever
        lenNums=len(nums)
        k=k%lenNums # The actual number of rotations

        self.reverse(nums, 0, lenNums-1) # Rev the whole array
        self.reverse(nums, 0, k-1) # Rev 0 to K
        self.reverse(nums, k, lenNums-1) # Rev k to len

        print(nums)
        pass

    def rotate4(self, nums, k):
        # Online answer, a lot easier, much more efficient

        # Get the actual number of rotations
        k = k % len(nums)      
        # Get the number of elements to move from the end to the beginning
        r = len(nums) - k
        # Store the elements to move
        new = nums[0:r]
        # Remove the elements from the beginning
        nums[0:r] = []
        # Append the stored elements to the end
        nums.extend(new)
        print(nums)
        pass

    def commaize(self, int):
        temp = str(int)
        temp = temp[::-1]
        count = 0
        for char in temp:
            count+=1
            if (count == 3):
                temp += str(',')
                count=0

        temp = temp[::-1]
        print(temp)

    def maxProfitFail(self, prices): #THIS DOES NOT PASS ALL TEST CASES: [2,4,1] example because it should not buy after it sells
        buy=prices[0]
        sell=prices[0]
        profit=0
        for i in range(1, len(prices)):
            if prices[i] < buy:         
                buy = prices[i]
            elif prices[i] > sell:
                sell = prices[i]

            print(buy, sell)

        profit = sell - buy

        if profit < 0:
            profit=0
        
        print(profit)

    def maxProfit(self, prices):
        buy = prices[0]
        profit = 0
        for i in range(1, len(prices)): # This changes profit (sells) only if it does not buy on the iteration. 
            if prices[i] < buy:
                buy=prices[i]
            elif prices[i] - buy > profit:
                profit = prices[i] - buy
        
        return profit
    
    def maxProfit2(self, prices):
        profit = 0
        start = prices[0]
        
        for i in range(0, len(prices)):
            #print("start: ", start)
            if start < prices[i]:
                profit += prices[i] - start
                #print("profit: ", profit)

            start = prices[i]

        return profit

    def canJumpFail(self, nums):
        goal = len(nums) - 1
        secondLast = len(nums) - 2
        maxJump = len(nums) - 2

        for i in range(len(nums)-2, -1, -1):
            print(nums[i])
            print("maxJ: ", maxJump)
            print("goal: ", goal, "\n")
            maxJump = nums[i]
            if i + maxJump >= goal:
                goal = i


        print("finalgoal: ", goal, "\n")
        if goal <= 0:
            return True
        return False
    
    def canJump(self, nums):
        goal = len(nums) - 1

        # Traverse from the second-to-last element to the start
        for i in range(len(nums) - 2, -1, -1):
            # If we can jump from position `i` to or beyond `goal`
            maxJump = nums[i]
            if i + maxJump >= goal:
                # Update the goal to the current position
                goal = i 

        # If we can reach the start (goal = 0), return True
        return goal == 0

    def generateParenthesis(self, n): # BACKTRACKING - This algorithm is kinda brilliant
        
        def solve(left, right, n, temp):
            if len(temp) == 2 * n:
                ans.append(temp)
                return

            # First exhaust all open parenthesis (if left > 0)
            if left > 0:
                solve(left - 1, right, n, temp + '(')

            # This is where the backtracking occurs, a bit different from Prolog
            if right > left:
                solve(left, right - 1, n, temp + ')')

        ans = []
        solve (n, n, n, "")
        return ans

    def mergeTwoListsPass(self, l1, l2): # THIS RECURSION METHOD WORKS; HOWEVER It doesnt compile on leetcode bc it needs to use ListNode
        def solve(l1, l2):
            if not l1:
                #print(l1, l2)
                return temp.extend(l2)
            
            if not l2:
                #print(l1, l2)
                return temp.extend(l1)

            if l1[0] <= l2[0]:
                temp.append(l1[0])
                solve(l1[1:], l2)
            else:
                temp.append(l2[0])
                solve(l1, l2[1:])

        temp = []
        solve (l1, l2)

        return temp
                
    # Definition for singly-linked list.
    # class ListNode:
    #     def __init__(self, val=0, next=None):
    #         self.val = val
    #         self.next = next
    # Definition for singly-linked list
    def mergeTwoLists(self, l1, l2): #USING ListNode
        def solve(l1, l2):
            if not l1:  # If l1 is empty, return l2
                return l2
            if not l2:  # If l2 is empty, return l1
                return l1

            if l1.val <= l2.val:  # Compare node values
                l1.next = solve(l1.next, l2)  # Merge the rest and attach to l1
                return l1
            else:
                l2.next = solve(l1, l2.next)  # Merge the rest and attach to l2
                return l2

        return solve(l1, l2)


if __name__ == "__main__":
    methods = Methods()
    #methods.removeElement([0, 1, 2, 2, 3, 0, 4, 2], 2)
    #methods.removeDuplicates2([0,0,1,1,1,1,2,3,3])
    #methods.majorityElement([2, 2, 1, 3, 2, 2, 1, 1, 2, 2, 4, 3, 2])
    #methods.rotate4([1,2,3,4,5,6,7], 5)
    #methods.commaize(612312)
    #methods.maxProfit([2,4,1])
    #methods.maxProfit2([7,1,2,1,2])
    #print(methods.canJump([2,0,0]))
    #print(methods.generateParenthesis(3))
    print(methods.mergeTwoLists([1,2,4], [1,3,4]))