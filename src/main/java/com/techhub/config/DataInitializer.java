package com.techhub.config;

import com.techhub.entity.Article;
import com.techhub.entity.Category;
import com.techhub.entity.Tag;
import com.techhub.entity.User;
import com.techhub.repository.ArticleRepository;
import com.techhub.repository.CategoryRepository;
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
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return;
        }
        log.info("初始化种子数据...");

        // 用户
        User admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .nickname("站长")
                .bio("TechHub 社区站长")
                .level("Lv5")
                .avatar("https://api.dicebear.com/7.x/identicon/svg?seed=admin")
                .build();
        User xiaoming = User.builder()
                .username("xiaoming")
                .password(passwordEncoder.encode("123456"))
                .nickname("小明")
                .bio("热爱后端开发")
                .level("Lv2")
                .avatar("https://api.dicebear.com/7.x/identicon/svg?seed=xiaoming")
                .build();
        userRepository.saveAll(List.of(admin, xiaoming));

        // 分类
        Category frontend = categoryRepository.save(Category.builder().name("前端").description("前端技术分享").build());
        Category backend = categoryRepository.save(Category.builder().name("后端").description("后端技术分享").build());
        Category ai = categoryRepository.save(Category.builder().name("人工智能").description("AI 与机器学习").build());
        Category android = categoryRepository.save(Category.builder().name("Android").description("移动端开发").build());
        Category devtools = categoryRepository.save(Category.builder().name("开发工具").description("效率工具").build());

        // 标签
        Tag java = tagRepository.save(Tag.builder().name("Java").build());
        Tag spring = tagRepository.save(Tag.builder().name("Spring Boot").build());
        Tag react = tagRepository.save(Tag.builder().name("React").build());
        Tag llm = tagRepository.save(Tag.builder().name("大模型").build());
        Tag perf = tagRepository.save(Tag.builder().name("性能优化").build());

        // 文章
        Article a1 = Article.builder()
                .title("Spring Boot 3 入门指南：从零搭建一个 RESTful 服务")
                .summary("本教程带你从零开始，用 Spring Boot 3 搭建一个完整的 RESTful API 服务，涵盖依赖注入、JPA、参数校验与统一异常处理。")
                .content("## 环境准备\n\n首先需要 JDK 17 和 Maven 3.8+。\n\n## 创建项目\n\n使用 Spring Initializr 快速生成项目骨架，选择 Web、Data JPA、Validation 依赖。\n\n## 编写第一个接口\n\n通过 @RestController 注解定义一个控制器，即可对外提供 REST 接口。\n\n## 总结\n\n本文覆盖了 Spring Boot 3 的核心开发流程。")
                .cover("https://images.unsplash.com/photo-1517694712202-14dd9538aa97?w=800&q=80")
                .author(admin)
                .category(backend)
                .tags(List.of(java, spring))
                .views(3200)
                .likes(280)
                .comments(35)
                .pinned(true)
                .build();

        Article a2 = Article.builder()
                .title("React 19 新特性解读")
                .summary("React 19 带来了 Server Actions、use() 钩子等新特性，本文逐一解读并给出实践建议。")
                .content("## 自动记忆化 Compiler\n\nReact Compiler 自动处理记忆化，useMemo 和 useCallback 可能成为历史。\n\n## Server Actions\n\n在服务端直接处理表单提交，无需额外 API 接口。\n\n## 总结\n\nReact 19 进一步简化了数据获取和状态管理。")
                .cover("https://images.unsplash.com/photo-1633356122544-f134324a6cee?w=800&q=80")
                .author(xiaoming)
                .category(frontend)
                .tags(List.of(react))
                .views(2100)
                .likes(180)
                .comments(22)
                .build();

        Article a3 = Article.builder()
                .title("大模型 RAG 实践：从文档到问答系统")
                .summary("完整实现一个 RAG 问答系统，覆盖文档切分、向量化、召回与生成。")
                .content("## RAG 架构\n\nRAG 由检索和生成两部分组成。\n\n## 文档切分策略\n\nchunk 大小的选择直接影响召回效果。\n\n## 向量化\n\n选择合适的 embedding 模型。\n\n## 总结\n\nRAG 是当前企业大模型落地的主流方案。")
                .cover("https://images.unsplash.com/photo-1677442136019-21780ecad995?w=800&q=80")
                .author(admin)
                .category(ai)
                .tags(List.of(llm))
                .views(4500)
                .likes(520)
                .comments(60)
                .build();

        articleRepository.saveAll(List.of(a1, a2, a3));
        log.info("种子数据初始化完成");
    }
}
