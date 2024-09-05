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
    

if __name__ == "__main__":
    methods = Methods()
    #methods.removeElement([0, 1, 2, 2, 3, 0, 4, 2], 2)
    #methods.removeDuplicates2([0,0,1,1,1,1,2,3,3])
    #methods.majorityElement([2, 2, 1, 3, 2, 2, 1, 1, 2, 2, 4, 3, 2])
    methods.rotate4([1,2,3,4,5,6,7], 5)

    