# Домашнее задание №7

Добавить к существующей системе паттерны: gateway api, service discovery, circuit breaker, external configuration - реализации данных паттернов можно найти в модулях spring cloud.

## Команда проекта

| Роль | Имя | Что выполнял |
|------|-----|--------------|
| Team Lead | Сымоник Вадим | Подключение существующих сервисов к Config + Eureka |
| Developer | Фурсова Валерия | Config Server + Eureka Server. Подготовка централизованных конфигураций для всех сервисов системы. |
| Developer | Франк Александр | API Gateway + Circuit Breaker. Защита от сбоев downstream-сервисов. |

### Порядок запуска

1. **Docker Compose** — поднимает инфраструктуру: Postgres, Kafka, MailHog.
2. **config-server :8888** — раздаёт конфигурации всем остальным сервисам.
3. **eureka-server :8761** — сервис регистрации, необходим Gateway и микросервисам.
4. **user-service :8080** и **notification-service :8081** — запускаются параллельно.
5. **apigateway-server :8085** — запускается последним, так как требует зарегистрированный user-service.

### Порты сервисов

| Сервис | Порт |
|--------|------|
| config-server | 8888 |
| eureka-server | 8761 |
| user-service | 8080 |
| notification-service | 8081 |
| apigateway-server | 8085 |
