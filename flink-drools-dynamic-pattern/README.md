# flink-drools-dynamic-pattern

## env 

- WSL
- openjdk 17
- apache flink 1.18.0
- Drools 8.44.0.Final

## how to use

- execute netcat command
```
・for rule data stream
nc -lk 9998

・for sensor data stream
nc -lk 9999
```

- execute main function 

- in your netcat terminal, type values as bellow and hit return 
```
・for rule data stream
1,50,Rule

・for sensor data stream
1,80,Sensor
2,90,Sensor
3,95,Sensor
```

## doc

- [Apache Flink](https://nightlies.apache.org/flink/flink-docs-stable/)
- [Drools](https://docs.drools.org/8.44.0.Final/drools-docs/drools/introduction/index.html)

