# SmartLibrary2 - ORM Tabanlı Akıllı Kütüphane Sistemi

Bu proje, bir üniversite kütüphane yönetim sisteminin Hibernate ORM kullanılarak modernize edilmiş halidir.

## 🛠 Kullanılan Teknolojiler
- **Dil:** Java
- **ORM:** Hibernate (Annotation tabanlı mapping)
- **Veritabanı:** SQLite
- **Bağımlılık Yönetimi:** Maven

## 🧱 Proje Yapısı
- `src/entity/`: Book, Student ve Loan modellerini içerir.
- `src/dao/`: Her model için CRUD işlemlerini (save, update, delete vb.) yöneten sınıflardır.
- `src/util/`: SessionFactory yönetimini sağlayan HibernateUtil sınıfıdır.
- `src/app/`: Kullanıcı etkileşimi için konsol menüsünü içerir.

## 🔗 İlişki Yapısı
- **Student - Loan:** OneToMany (Bir öğrenci birden fazla kitap ödünç alabilir).
- **Loan - Book:** OneToOne (Bir ödünç kaydı tek bir kitaba aittir).

## 🚀 Çalıştırma
Projeyi çalıştırmak için terminale şu komutu yazabilirsiniz:
`mvn exec:java "-Dexec.mainClass=com.smartlibraryplus.app.MainApp"`

---

## Encoding (Türkçe karakter desteği) ⚠️
Bazı Windows terminal ayarlarında Türkçe karakterler düzgün görüntülenmeyebilir. Projeyi UTF-8 ile derleyip çalıştırmak için aşağıdaki adımları kullanın:

- Maven komutları JVM parametresi `-Dfile.encoding=UTF-8` ile çalışacak şekilde yapılandırıldı.
- Windows terminalde çalıştırmadan önce kod sayfasını UTF-8'e alın (geçici):

```
chcp 65001
```

- VS Code içinde `Tasks` ile çalıştırıyorsanız, `JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8` ortam değişkenini kullanın.

PowerShell kullanıyorsanız terminalde şu komutla çıktıyı UTF-8'e alın:

```
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
```

Eğer hâlâ sorun yaşanırsa, terminalinizin varsayılan kod sayfası veya VS Code terminal ayarlarını (`terminal.integrated.defaultEncoding`) kontrol edin. Sistem genelinde kalıcı çözüm için (Windows) Denetim Masası -> Bölge -> Yönetim -> Sistem yerel ayarını 'Beta: Use Unicode UTF-8 for worldwide language support' olarak işaretleyebilirsiniz (yeniden başlatma gerektirir).

### Kolay çalıştırma (Windows)
İsterseniz doğrudan `run.bat` kullanabilirsiniz — bu betik `chcp 65001` ile kod sayfasını UTF-8'e alır, PowerShell ile çıktı kodlamasını ayarlar ve uygulamayı UTF-8 ile başlatır:

```
run.bat
```

Unix benzeri sistemlerde `./run.sh` kullanabilirsiniz.