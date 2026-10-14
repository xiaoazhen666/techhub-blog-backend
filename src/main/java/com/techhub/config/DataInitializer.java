package com.techhub.config;

import com.techhub.entity.Article;
import com.techhub.entity.Category;
import com.techhub.entity.Comment;
import com.techhub.entity.Tag;
import com.techhub.entity.User;
import com.techhub.repository.ArticleRepository;
import com.techhub.repository.CategoryRepository;
import com.techhub.repository.CommentRepository;
import com.techhub.repository.TagRepository;
import com.techhub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void run(String... args) {
        // 如果文章数量不足（说明是旧版种子数据或空库），清空后重新初始化
        if (articleRepository.count() >= 10) {
            return;
        }
        log.info("检测到数据不完整（当前 {} 篇文章），重新初始化种子数据...", articleRepository.count());

        // 清空旧数据（按依赖顺序）：先评论，再文章（先清 tags 关联），再 tag、category、user
        commentRepository.deleteAll();
        for (Article a : articleRepository.findAll()) {
            a.getTags().clear();
        }
        articleRepository.deleteAll();
        tagRepository.deleteAll();
        categoryRepository.deleteAll();
        userRepository.deleteAll();

        // ===== 用户 =====
        User admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .nickname("站长")
                .bio("TechHub 社区站长，专注全栈与架构，热爱分享技术。")
                .level("Lv6")
                .followers(1200)
                .following(89)
                .likes(3200)
                .avatar("https://api.dicebear.com/7.x/identicon/svg?seed=admin")
                .build();
        User xiaoming = User.builder()
                .username("xiaoming")
                .password(passwordEncoder.encode("123456"))
                .nickname("小明")
                .bio("后端工程师，Java 与分布式系统爱好者。")
                .level("Lv4")
                .followers(680)
                .following(120)
                .likes(1500)
                .avatar("https://api.dicebear.com/7.x/identicon/svg?seed=xiaoming")
                .build();
        User lili = User.builder()
                .username("lili")
                .password(passwordEncoder.encode("123456"))
                .nickname("Lili")
                .bio("前端开发，React 与设计系统。")
                .level("Lv3")
                .followers(430)
                .following(65)
                .likes(980)
                .avatar("https://api.dicebear.com/7.x/identicon/svg?seed=lili")
                .build();
        User alex = User.builder()
                .username("alex")
                .password(passwordEncoder.encode("123456"))
                .nickname("Alex")
                .bio("AI 算法工程师，大模型应用方向。")
                .level("Lv5")
                .followers(1500)
                .following(200)
                .likes(4100)
                .avatar("https://api.dicebear.com/7.x/identicon/svg?seed=alex")
                .build();
        userRepository.saveAll(List.of(admin, xiaoming, lili, alex));

        // ===== 分类 =====
        Category frontend = categoryRepository.save(Category.builder().name("前端").description("前端技术分享").build());
        Category backend = categoryRepository.save(Category.builder().name("后端").description("后端技术分享").build());
        Category ai = categoryRepository.save(Category.builder().name("人工智能").description("AI 与机器学习").build());
        Category android = categoryRepository.save(Category.builder().name("Android").description("移动端开发").build());
        Category devtools = categoryRepository.save(Category.builder().name("开发工具").description("效率工具").build());
        Category db = categoryRepository.save(Category.builder().name("数据库").description("数据库与存储").build());
        Category cloud = categoryRepository.save(Category.builder().name("云计算").description("云原生与容器").build());

        // ===== 标签 =====
        Tag java = tagRepository.save(Tag.builder().name("Java").build());
        Tag spring = tagRepository.save(Tag.builder().name("Spring Boot").build());
        Tag react = tagRepository.save(Tag.builder().name("React").build());
        Tag vue = tagRepository.save(Tag.builder().name("Vue").build());
        Tag llm = tagRepository.save(Tag.builder().name("大模型").build());
        Tag perf = tagRepository.save(Tag.builder().name("性能优化").build());
        Tag docker = tagRepository.save(Tag.builder().name("Docker").build());
        Tag mysql = tagRepository.save(Tag.builder().name("MySQL").build());

        // ===== 文章 =====
        Article a1 = article("Spring Boot 3 入门指南：从零搭建一个 RESTful 服务",
                "本教程带你从零开始，用 Spring Boot 3 搭建一个完整的 RESTful API 服务，涵盖依赖注入、JPA、参数校验与统一异常处理。",
                "## 环境准备\n\n首先需要 JDK 17 和 Maven 3.8+，建议使用 IntelliJ IDEA 社区版。\n\n## 创建项目\n\n使用 Spring Initializr 快速生成项目骨架，选择 Web、Data JPA、Validation 依赖。\n\n## 编写第一个接口\n\n通过 @RestController 注解定义一个控制器，即可对外提供 REST 接口。\n\n## 统一异常处理\n\n使用 @RestControllerAdvice 全局捕获异常，返回统一的结构。\n\n## 总结\n\n本文覆盖了 Spring Boot 3 的核心开发流程，掌握后可以快速搭建自己的服务。",
                "https://images.unsplash.com/photo-1517694712202-14dd9538aa97?w=800&q=80",
                admin, backend, List.of(java, spring), 3200, 280, true);

        Article a2 = article("React 19 新特性解读：Compiler 与 Server Actions",
                "React 19 带来了 Server Actions、use() 钩子、React Compiler 等新特性，本文逐一解读并给出实践建议。",
                "## 自动记忆化 Compiler\n\nReact Compiler 自动处理记忆化，useMemo 和 useCallback 可能成为历史。\n\n## Server Actions\n\n在服务端直接处理表单提交，无需额外 API 接口。\n\n## use() 钩子\n\n在渲染期间读取 Promise 和 Context，简化数据获取。\n\n## 总结\n\nReact 19 进一步简化了数据获取和状态管理。",
                "https://images.unsplash.com/photo-1633356122544-f134324a6cee?w=800&q=80",
                lili, frontend, List.of(react), 2100, 180, false);

        Article a3 = article("大模型 RAG 实践：从文档到问答系统",
                "完整实现一个 RAG 问答系统，覆盖文档切分、向量化、召回与生成。",
                "## RAG 架构\n\nRAG 由检索和生成两部分组成，核心是把外部知识注入到生成过程中。\n\n## 文档切分策略\n\nchunk 大小的选择直接影响召回效果，一般 256~512 token 为宜。\n\n## 向量化\n\n选择合适的 embedding 模型，中文场景可考虑 bge 系列。\n\n## 总结\n\nRAG 是当前企业大模型落地的主流方案。",
                "https://images.unsplash.com/photo-1677442136019-21780ecad995?w=800&q=80",
                alex, ai, List.of(llm), 4500, 520, true);

        Article a4 = article("MySQL 索引优化实战：从慢查询到毫秒级响应",
                "一次完整的 MySQL 慢查询排查与优化记录，涵盖索引设计原则、执行计划分析与常见误区。",
                "## 背景\n\n线上一个列表查询从 3 秒优化到 20 毫秒的全过程。\n\n## 定位慢查询\n\n开启 slow_query_log，找到耗时最长的 SQL。\n\n## 分析执行计划\n\n使用 EXPLAIN 查看是否走索引、是否全表扫描。\n\n## 索引设计原则\n\n最左前缀原则、覆盖索引、避免回表。\n\n## 总结\n\n索引不是越多越好，要结合查询场景设计。",
                "https://images.unsplash.com/photo-1544383835-bda2bc66a55d?w=800&q=80",
                xiaoming, db, List.of(mysql, perf), 2800, 230, false);

        Article a5 = article("Docker 实战：从镜像构建到多阶段部署",
                "系统讲解 Docker 镜像优化、多阶段构建、docker-compose 编排，以及生产环境的注意事项。",
                "## 为什么用 Docker\n\n环境一致性是最大的痛点，Docker 解决了它。\n\n## 多阶段构建\n\n构建阶段与运行阶段分离，镜像体积可缩小 90%。\n\n## docker-compose 编排\n\n一条命令启动多个服务。\n\n## 总结\n\nDocker 是现代部署的基础设施。",
                "https://images.unsplash.com/photo-1605745341112-85968b19335b?w=800&q=80",
                admin, cloud, List.of(docker), 1900, 150, false);

        Article a6 = article("Vue 3 组合式 API 最佳实践",
                "深入理解 Vue 3 的组合式 API，用 composables 组织可复用的逻辑。",
                "## 从选项式到组合式\n\n组合式 API 让逻辑组织更清晰。\n\n## 自定义 composables\n\n抽取可复用的状态逻辑。\n\n## 与 TypeScript 结合\n\n组合式 API 天然友好于类型推断。\n\n## 总结\n\n合理使用组合式 API，代码可维护性大幅提升。",
                "https://images.unsplash.com/photo-1605379399642-870262d3d051?w=800&q=80",
                lili, frontend, List.of(vue), 1650, 120, false);

        Article a7 = article("Java 并发编程：从 synchronized 到 CompletableFuture",
                "梳理 Java 并发编程的核心知识，从锁、线程池到异步编程，附实战示例。",
                "## 线程安全基础\n\n可见性、原子性、有序性三大问题。\n\n## synchronized 与 Lock\n\n锁的升级过程与适用场景。\n\n## 线程池\n\nThreadPoolExecutor 参数详解。\n\n## CompletableFuture\n\n异步编排，告别回调地狱。\n\n## 总结\n\n并发编程需要理论与实战结合。",
                "https://images.unsplash.com/photo-1515879218367-8466d910aaa4?w=800&q=80",
                xiaoming, backend, List.of(java, perf), 2400, 200, false);

        Article a8 = article("Spring Boot 微服务架构演进之路",
                "从单体到微服务，分享 Spring Cloud 生态下的架构演进经验与踩坑记录。",
                "## 为什么要微服务\n\n单体架构的痛点在规模增长后愈发明显。\n\n## 服务拆分原则\n\n按业务域拆分，避免过度拆分。\n\n## 服务治理\n\n注册发现、配置中心、网关。\n\n## 总结\n\n微服务不是银弹，要量力而行。",
                "https://images.unsplash.com/photo-1555066931-4365d14bab8c?w=800&q=80",
                admin, backend, List.of(spring, docker), 3100, 260, false);

        Article a9 = article("Prompt Engineering：大模型对话的实用技巧",
                "总结日常使用大模型的高效 Prompt 技巧，让 AI 输出更符合预期。",
                "## 结构化提问\n\n角色、任务、约束、示例。\n\n## Few-shot 示例\n\n给模型几个例子，效果立竿见影。\n\n## 思维链\n\n让模型一步步推理。\n\n## 总结\n\n好的 Prompt 是反复打磨出来的。",
                "https://images.unsplash.com/photo-1620712943543-bcc4688e7485?w=800&q=80",
                alex, ai, List.of(llm), 3800, 410, false);

        Article a10 = article("Git 高效工作流：分支管理与团队协作",
                "一套实用的 Git 分支管理规范，涵盖 feature 分支、PR 评审与合并策略。",
                "## 常用分支模型\n\nmain、develop、feature 分支。\n\n## 提交规范\n\nConventional Commits 让提交历史可读。\n\n## PR 评审\n\nCode Review 的最佳实践。\n\n## 总结\n\n规范的流程让团队协作更顺畅。",
                "https://images.unsplash.com/photo-1618401471353-b98afee0b2eb?w=800&q=80",
                lili, devtools, List.of(), 1350, 90, false);

        Article a11 = article("Android 性能优化：从卡顿到流畅",
                "Android 应用的性能优化实践，涵盖启动优化、布局优化与内存管理。",
                "## 启动优化\n\n冷启动、热启动的优化手段。\n\n## 布局优化\n\n减少嵌套层级，避免过度绘制。\n\n## 内存管理\n\n内存泄漏的排查与治理。\n\n## 总结\n\n性能优化是一个持续的过程。",
                "https://images.unsplash.com/photo-1512941937669-90a1b58e7e9c?w=800&q=80",
                xiaoming, android, List.of(perf), 980, 75, false);

        Article a12 = article("Kubernetes 入门：从 Pod 到 Deployment",
                "面向初学者的 K8s 入门指南，理解核心概念并跑通第一个应用。",
                "## 核心概念\n\nPod、Service、Deployment 的关系。\n\n## 部署第一个应用\n\n用 YAML 描述应用并部署。\n\n## 服务暴露\n\nService 与 Ingress。\n\n## 总结\n\nK8s 学习曲线陡峭，但值得投入。",
                "https://images.unsplash.com/photo-1667372393119-3d4c48d07fc9?w=800&q=80",
                admin, cloud, List.of(docker), 2200, 170, false);

        articleRepository.saveAll(List.of(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12));

        // ===== 评论 =====
        commentRepository.saveAll(List.of(
                comment("写得太清晰了，跟着做一遍就能跑通，感谢分享！", a1, xiaoming),
                comment("统一异常处理那部分对我帮助很大，之前一直没搞明白。", a1, lili),
                comment("React 19 的 Compiler 确实省事，不过项目升级还是要谨慎。", a2, xiaoming),
                comment("Server Actions 我试过，配合 Next.js 很好用。", a2, admin),
                comment("RAG 的 chunk 大小那块讲得细，召回率确实敏感。", a3, lili),
                comment("期待作者出一篇关于 embedding 模型选型的。", a3, xiaoming),
                comment("慢查询排查的步骤很实用，收藏了。", a4, alex),
                comment("覆盖索引那段讲得透彻，终于理解了回表。", a4, lili),
                comment("多阶段构建让镜像小了好多，亲测有效。", a5, xiaoming),
                comment("并发编程这块一直是弱项，这篇帮我理清了思路。", a7, lili),
                comment("线程池参数那段建议再展开讲讲拒绝策略。", a7, alex)
        ));

        log.info("种子数据初始化完成");
    }

    private Article article(String title, String summary, String content, String cover,
                            User author, Category category, List<Tag> tags,
                            int views, int likes, boolean pinned) {
        return Article.builder()
                .title(title)
                .summary(summary)
                .content(content)
                .cover(cover)
                .author(author)
                .category(category)
                .tags(tags)
                .views(views)
                .likes(likes)
                .comments(0)
                .pinned(pinned)
                .build();
    }

    private Comment comment(String content, Article article, User user) {
        return Comment.builder()
                .content(content)
                .article(article)
                .user(user)
                .likes(0)
                .build();
    }
}
