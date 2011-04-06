def bubble_sort(nums=[]):
    l = len(nums)
    for i in range(l):
        for j in range(i + 1, l):
            if nums[i] > nums[j]:
                nums[i], nums[j] = nums[j], nums[i] 

def choose_sort(nums=[]):
    l = len(nums)
    for i in range(l):
        index, min = i, nums[i]
        for j in range(i + 1, l):
            if nums[j] < min:
                index, min = j, nums[j] 
        nums[i], nums[index] = min, nums[i]

nums = [9, 8, 7, 11, 6, 5, 4, 3, 2, 1]
#bubble_sort(nums)
#choose_sort(nums)
print nums
