

Feature: login ke belanjaqu

  Scenario Outline: Saya dapat login belanjaqu menggunakan id atau nomor handphone paytren

  Given Saya berada pada halaman belanjaqu
  When Saya klik side bar
  And Saya klik tombol login
  Then Sistem menampilkan halaman login
  Then Saya klik login dengan paytren
  And Saya mengisi nomor ponsel "<nomorponsel>"
  And Saya mengisi pin "<pin>"
  And Saya mengisi captcha
  Then Saya Klik Login
  Then Berhasil login berada pada halaman utama belanjaqu


    Examples:
      | nomorponsel   | pin   |
      | 082116515155  | 100000|
