// en.js
export default {
  message: {
    hello: "Hello, World!",
    logout: "Đăng xuất",
    language: "ngôn ngữ",
    english: "Tiếng Anh",
    chinese: "Tiếng Trung",
    vietnamese: "Tiếng Việt",
  },
  languages: {
    language: 'ngôn ngữ',
    vn: 'Tiếng Việt',
    cn: 'Tiếng Trung',
    en: 'Tiếng Anh',
    kr: 'Tiếng Hàn',
    jp: 'Tiếng Nhật',
    rs: 'Tiếng Nga',
    fr: 'Tiếng Pháp'
  },
  loginPage: {
    register: 'Đăng ký',
    login: 'Đăng nhập',
    account: 'Số tài khoản',
    password: 'Mật khẩu',
    verificationCode: 'Mã xác nhận',
    enterAccount: 'Vui lòng nhập số tài khoản của bạn',
    enterPassword: 'Vui lòng nhập mật khẩu',
    rememberMe : 'Ghi nhớ mật khẩu',
  },

  pageCommon: {
    search: 'Tìm kiếm',
    reset: 'Đặt lại',
    add: 'Thêm',
    update: 'Cập nhật',
    delete: 'Xoá',
  },

  // 一级菜单《用户管理》
  muser: {
    userManage: 'Quản lý người dùng',

    userList: 'Danh sách người xem',
    userGrade: 'Quản lý cấp độ',
    withdrawCustomer: 'Rút tiền khách',
    withdrawEmployee: 'Rút tiền nhân viên',
  },

  // 用户列表
  userPage: {
    account: 'tài khoản',
    withdrawCustomerBtn: 'Rút tiền khách',
    withdrawEmployeeBtn: 'Rút tiền nhân viên',
    column: {
      invitationCode: 'MÃ MỜI',
      loginAccount: 'TÊN',
      SETTING_ORDER: 'CÀI ĐẶT ĐƠN HÀNG',
      settingOrder: 'Cài đặt đơn hàng',
      action: 'HÀNH ĐỘNG',
      inviterCode: 'MÃ NGƯỜI GIỚI THIỆU',
      regsterTime: 'THỜI GIAN ĐĂNG KÝ',
      lastLoginIp: 'IP ĐĂNG NHẬP LẦN CUỐI',
      phoneNumber: 'SỐ ĐIỆN THOẠI',
      accountBalance: 'SỐ DƯ',
      userStatus: 'TRẠNG THÁI',
      userLevel: 'CẤP ĐỘ',
      brushNumber: 'SỐ LƯỢNG ĐẶT HÀNG'
    },
    //行内转换
    typeGuest: 'Khách',
    typeEmployee: 'Nhân viên',
    typeUnknown: 'Không biết',
    noData: 'Không có dữ liệu',
    statusUnlock: 'ACTIVE',
    statusLock: 'LOCKED',

    //《行为》内按钮
    colAct: {
      width: '510',
      changeBalence: 'Thay đổi số dư tài khoản',
      changeBank: 'Sửa đổi ngân hàng',
      switchEmp: 'Chuyển sang nhân viên',
      switchGuest: 'Chuyển sang khách',
      orderHistory: 'Lịch sử nhận đơn',
      lockAccount: 'Khóa tài khoản',
      unlockAccount: 'Mở tài khoản',
      deleteAccount: 'Xóa tài khoản',
      info: 'Thông tin nhóm',
      changeInfo: 'Sửa đổi thông tin tài khoản',
      sendMsg: 'Gửi tin nhắn'
    },

    //《更改账户余额》弹出窗口
    balForm: {
      title: 'Cập nhật',
      account: 'Tài khoản',
      phone: 'Số điện thoại',
      origin: 'số dư',
      newNum: 'Số tiền',
      selectReason: 'Chọn một lý do',
      noReason: ' Không có lý do',
      adjust: ' Điều chỉnh số dư',
      or: 'Hoặc',
      cancel: 'Hủy',
      save: 'Tạo mới',
      requireNum: 'Trường số tiền là bắt buộc.',
      requireReason: 'Lý do nhập',
    }
  }

};
