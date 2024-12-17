package com.example.demo.manager;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.models.Boss;
import com.example.demo.models.UserPlane;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

import java.util.*;

public class CollisionManager {

    private final Group root;
    private final double screenWidth;
    private final double screenHeight;

    private final List<ActiveActorDestructible> enemyUnits;
    private final List<ActiveActorDestructible> userProjectiles;
    private final List<ActiveActorDestructible> enemyProjectiles;
    private final Map<ActiveActorDestructible, Rectangle> boundingBoxHighlights;
    private final UserPlane user;

    private Boss boss;

    public CollisionManager(Group root,
                            double screenWidth,
                            double screenHeight,
                            List<ActiveActorDestructible> enemyUnits,
                            List<ActiveActorDestructible> userProjectiles,
                            List<ActiveActorDestructible> enemyProjectiles,
                            Map<ActiveActorDestructible, Rectangle> boundingBoxHighlights,
                            UserPlane user) {
        this.root = root;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.enemyUnits = enemyUnits;
        this.userProjectiles = userProjectiles;
        this.enemyProjectiles = enemyProjectiles;
        this.boundingBoxHighlights = boundingBoxHighlights;
        this.user = user;
    }

    public void setBoss(Boss boss) {
        this.boss = boss;
    }

    public void handleCollisions(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2) {
        for (ActiveActorDestructible actor : actors2) {
            for (ActiveActorDestructible otherActor : actors1) {
                if (actor.getAdjustedBounds().intersects(otherActor.getAdjustedBounds())) {
                    actor.takeDamage();
                    otherActor.takeDamage();
                }
            }
        }
    }

    public void handlePlaneCollisions(List<ActiveActorDestructible> friendlyUnits) {
        handleCollisions(friendlyUnits, enemyUnits);
    }

    public void handleUserProjectileCollisions(Runnable loseGameAction) {
        List<ActiveActorDestructible> enemiesToRemove = new ArrayList<>();
        List<ActiveActorDestructible> projectilesToRemove = new ArrayList<>();

        for (ActiveActorDestructible enemy : enemyUnits) {
            if (!enemy.isVisibleOnScreen(screenWidth, screenHeight)) {
                continue;
            }
            for (ActiveActorDestructible projectile : userProjectiles) {
                if (enemy.getAdjustedBounds().intersects(projectile.getAdjustedBounds())) {
                    enemy.takeDamage();
                    projectile.takeDamage();

                    if (enemy.isDestroyed()) {
                        user.incrementKillCount();
                        enemiesToRemove.add(enemy);

                        Rectangle highlight = boundingBoxHighlights.remove(enemy);
                        if (highlight != null) {
                            root.getChildren().remove(highlight);
                        }
                    }
                    if (projectile.isDestroyed()) {
                        projectilesToRemove.add(projectile);
                    }
                }
            }
        }

        if (boss != null) {
            for (ActiveActorDestructible projectile : userProjectiles) {
                if (boss.getAdjustedBounds().intersects(projectile.getAdjustedBounds())) {
                    boss.takeDamage();
                    projectile.takeDamage();
                    if (projectile.isDestroyed()) {
                        projectilesToRemove.add(projectile);
                    }
                }
            }
        }

        for (ActiveActorDestructible projectile : enemyProjectiles) {
            if (user.getAdjustedBounds().intersects(projectile.getAdjustedBounds())) {
                user.takeDamage();
                projectile.takeDamage();
                if (projectile.isDestroyed()) {
                    projectilesToRemove.add(projectile);
                }

                if (user.isDestroyed()) {
                    loseGameAction.run();
                    return;
                }
            }
        }

        root.getChildren().removeAll(enemiesToRemove);
        root.getChildren().removeAll(projectilesToRemove);
        enemyUnits.removeAll(enemiesToRemove);
        userProjectiles.removeAll(projectilesToRemove);
        enemyProjectiles.removeAll(projectilesToRemove);
    }

    public void handleEnemyProjectileCollisions(List<ActiveActorDestructible> friendlyUnits) {
        handleCollisions(enemyProjectiles, friendlyUnits);
    }
}
