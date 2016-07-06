# domain_crawler

To use the application

1. Run 'mvn clean -U package'

2. Run 'java -jar ./target/domain_crawler-*.jar -d=[url-to-crawl] -o=[output-file-path]'
    Example: 'java -jar target/domain_crawler-*.jar -d=http://www.google.com -o=/home/Tom/Desktop/test.csv'