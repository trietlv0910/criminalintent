

//--- Begin

- Đọc dữ liệu từ database thông repository (CrimeListViewModel)
  - Tại CrimeListViewModel --> tạo background thread 
    - tại background thread --> gọi getCrimes từ repository
    
- Chức năng chỉnh sửa sai phạm
  - Khi người dùng  click vào item --> chuyển sang màn hình chỉnh sửa
    - shared view model (CrimeListViewModel sẽ được tạo với activityViewModels)

// LiveData

- Room Persistence sẽ xử lý các query methods nếu những method này return LiveData.
- LiveData
  - LiveData: Dùng để theo dõi dữ liệu
  - MutableLiveData: Dùng để thay đổi/cập nhật dữ liệu

// step 1: Cập nhật DAO
- DAO -> chuyển tất các query method sang dạng return LiveData
  - Bởi vì: Repository sử dụng DAO để truy xuất database --> Thay đổi DAO sẽ phải
  thay đổi Repository

// step 2: cập nhật repository
Tương tự bước 1.
- Bởi vì Repository Pattern tách các xử lý business logic ra khỏi dữ liệu.
  --> view model sẽ thông qua repository để truy xuất dữ liệu

// step 3: cập nhật view model

- khởi tạo crimesLiveData thông qua repository
  -> Cập nhật CrimeListFragment

//step4: Cập nhật CrimeListFragment
- observe livedata thông qua viewmodel
  - LifeCycleOwner và callback
    - LifeCycleOwner: activity, fragment, fragment view (viewLifeCycleOwner)
    - callback: được gọi khi dữ liệu mà live data chứa xảy ra thay đổi.

// shared view model problem
- https://docs.google.com/presentation/d/1R5PeX8PdRtMZw6UKg8-boQ07qPcNhtTFnSZrPITj4xs/edit#slide=id.g27670364232_0_27

// step 5: cho mỗi fragment là mỗi viewmodel
- Thay đổi CrimeListFragment: khởi tạo viewmodel thông qua viewModels method
- Tạo CrimeDetailViewModel cho CrimeFragment

// step 6: CrimeDetailViewModel
- khi item tại CrimeListFragment được nhấn --callback--> activity
- activity thực hiện load CrimeFragment và truyền vào uuid.
- CrimeDetailViewModel cần cung cấp chức năng để lấy đối tượng crime từ DB dựa vào ID.
  - tạo loadCrime method nhận vào uuid
  - thông qua mutable livedata để thay đổi dữ liệu UUID.
  