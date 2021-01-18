#
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html.
# Run `pod lib lint fl_ali_realperson.podspec' to validate before publishing.
#
Pod::Spec.new do |s|
  s.name             = 'fl_ali_realperson'
  s.version          = '0.0.1'
  s.summary          = 'A new flutter plugin project.'
  s.description      = <<-DESC
A new flutter plugin project.
                       DESC
  s.homepage         = 'http://example.com'
  s.license          = { :file => '../LICENSE' }
  s.author           = { 'Your Company' => 'email@example.com' }
  s.source           = { :path => '.' }
  s.source = { :git => "https://hub.fastgit.org/CocoaPods/Specs.git" } 
  # s.source = { :git => "https://mirrors.tuna.tsinghua.edu.cn/git/CocoaPods/Specs.git"}
  s.source = { :git => "https://hub.fastgit.org/aliyun/aliyun-specs.git" } 
  s.source_files = 'Classes/**/*'
  s.public_header_files = 'Classes/**/*.h'
  s.dependency 'Flutter'
  s.dependency "AlicloudRPSDK" , "~> 3.6.0"  
  s.dependency "AliyunOSSiOS" , "~> 2.10.8"  
  s.dependency "AlicloudSGSecurityBody" , "~> 5.4.12987657-rp"  
  s.platform = :ios, '9.0'

  # Flutter.framework does not contain a i386 slice. Only x86_64 simulators are supported.
  s.pod_target_xcconfig = { 'DEFINES_MODULE' => 'YES', 'VALID_ARCHS[sdk=iphonesimulator*]' => 'x86_64' }
end
